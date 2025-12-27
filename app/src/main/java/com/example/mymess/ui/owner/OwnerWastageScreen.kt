package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mymess.domain.model.WastageEntry
import com.example.mymess.domain.repository.MessRepository
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class OwnerWastageViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionManager: com.example.mymess.domain.manager.SessionManager
) : ViewModel() {

    private val _wastageList = MutableStateFlow<List<WastageEntry>>(emptyList())
    val wastageList: StateFlow<List<WastageEntry>> = _wastageList
    
    // Simple stat
    val totalWastageKg = MutableStateFlow(0.0)

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val user = sessionManager.getCurrentUser()
            val messId = user?.linkedMesses?.firstOrNull() ?: "mess-01"
            val list = repository.getWastage(messId)
            _wastageList.value = list.sortedByDescending { it.date }
            totalWastageKg.value = list.sumOf { it.quantityKg }
        }
    }

    fun addWastage(meal: String, kg: Double, reason: String) {
        viewModelScope.launch {
            val user = sessionManager.getCurrentUser()
            val messId = user?.linkedMesses?.firstOrNull() ?: "mess-01"
            
            val entry = WastageEntry(
                id = UUID.randomUUID().toString(),
                messId = messId,
                date = System.currentTimeMillis(),
                mealType = meal,
                quantityKg = kg,
                reason = reason
            )
            repository.logWastage(entry)
            loadData()
        }
    }
}

@Composable
fun OwnerWastageScreen(
    navController: NavController,
    viewModel: OwnerWastageViewModel = hiltViewModel()
) {
    val list by viewModel.wastageList.collectAsState()
    val totalKg by viewModel.totalWastageKg.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(title = "Food Wastage Tracker", onBack = { navController.popBackStack() })

            // Summary Card
            Card(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                colors = CardDefaults.cardColors(containerColor = ErrorRed),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total Wasted (All Time)", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha=0.8f))
                    Text("${String.format("%.1f", totalKg)} kg", style = MaterialTheme.typography.displayMedium, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Keep it simpler to save costs!", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha=0.8f))
                }
            }
            
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Recent Logs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                }
                
                items(list) { item ->
                    WastageItemCard(item)
                }
            }
        }
        
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
            containerColor = ErrorRed,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Log Wastage")
        }
    }

    if(showDialog) {
        WastageSheet(
            onDismiss = { showDialog = false },
            onSave = { meal, kg, reason ->
                viewModel.addWastage(meal, kg, reason)
                showDialog = false
            }
        )
    }
}

@Composable
fun WastageItemCard(item: WastageEntry) {
    val date = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(item.date))
    CorporateCard {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(item.mealType, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Text(item.reason, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
            Text("${item.quantityKg} kg", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = ErrorRed)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WastageSheet(onDismiss: () -> Unit, onSave: (String, Double, String) -> Unit) {
    var meal by remember { mutableStateOf("Lunch") }
    var kg by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("Leftovers") }
    
    CorporateBottomSheet(
        onDismissRequest = onDismiss,
        title = "Log Daily Wastage"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ModernInput(
                value = kg, 
                onValueChange = { kg = it }, 
                label = "Quantity (kg)", 
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            ModernInput(value = meal, onValueChange = { meal = it }, label = "Meal (Breakfast/Lunch/Dinner)")
            ModernInput(value = reason, onValueChange = { reason = it }, label = "Reason")
            
            SheetActionButtons(
                primaryText = "Log Entry",
                onPrimaryClick = {
                    val kgVal = kg.toDoubleOrNull() ?: 0.0
                    if(kgVal > 0) onSave(meal, kgVal, reason)
                },
                onSecondaryClick = onDismiss,
                primaryColor = ErrorRed
            )
        }
    }
}
