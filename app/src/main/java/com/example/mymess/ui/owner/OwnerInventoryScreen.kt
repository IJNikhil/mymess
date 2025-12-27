package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.domain.model.InventoryItem
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import androidx.compose.material.icons.filled.Menu

@Composable
fun OwnerInventoryScreen(
    navController: NavController,
    viewModel: OwnerInventoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Sheet State
    var showSheet by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<InventoryItem?>(null) } // Null = Add Mode

    if (showSheet) {
        AddInventorySheet(
            initialName = editingItem?.name ?: "",
            initialQty = editingItem?.quantity?.toString() ?: "",
            initialUnit = editingItem?.unit ?: "kg",
            onDismiss = { showSheet = false; editingItem = null },
            onSave = { name, qty, unit ->
                if (editingItem == null) {
                    viewModel.addItem(name, qty, unit)
                } else {
                    viewModel.updateItem(editingItem!!.id, name, qty, unit)
                }
                showSheet = false
                editingItem = null
            }
        )
    }

    var selectedFilter by remember { mutableStateOf("All") } // All, Low Stock
    
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = "Inventory", 
                onBack = { navController.popBackStack() }
            )
            
            // Filter Chips
            Row(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = selectedFilter == "All",
                    onClick = { selectedFilter = "All" },
                    label = { Text("All Items") }
                )
                FilterChip(
                    selected = selectedFilter == "Low Stock",
                    onClick = { selectedFilter = "Low Stock" },
                    label = { Text("Low Stock") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = ErrorRed,
                        selectedLabelColor = Color.White
                    )
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                when (val state = uiState) {
                    is InventoryUiState.Loading -> {
                         Column {
                             SkeletonListRow()
                             SkeletonListRow()
                             SkeletonListRow()
                         }
                    }
                    is InventoryUiState.Error -> Text(state.message, modifier = Modifier.align(Alignment.Center), color = ErrorRed)
                    is InventoryUiState.Success -> {
                        val filteredItems = if(selectedFilter == "Low Stock") state.items.filter { it.quantity <= it.minThreshold } else state.items
                        
                        InventoryList(
                            items = filteredItems,
                            onEdit = { item -> editingItem = item; showSheet = true },
                            onDelete = { item -> viewModel.deleteItem(item.id) }
                        )
                    }
                }
            }
        }
        
        FloatingActionButton(
            onClick = { editingItem = null; showSheet = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
            containerColor = CorporateNavy,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Item")
        }
    }
}

@Composable
fun InventoryList(
    items: List<InventoryItem>,
    onEdit: (InventoryItem) -> Unit,
    onDelete: (InventoryItem) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        item {
             // Clean Enterprise Header
             Row(modifier = Modifier
                 .fillMaxWidth()
                 .background(MaterialTheme.colorScheme.surface)
                 .padding(horizontal = 24.dp, vertical = 12.dp)
             ) {
                 Text("ITEM", modifier = Modifier.weight(1.5f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                 Text("QUANTITY", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
                 Text("ACTIONS", modifier = Modifier.weight(0.8f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.outline)
             }
             HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        }
        
        items(items) { item ->
            InventoryItemRow(item, onEdit, onDelete)
        }
    }
}

@Composable
fun InventoryItemRow(
    item: InventoryItem,
    onEdit: (InventoryItem) -> Unit,
    onDelete: (InventoryItem) -> Unit
) {
    val isLowStock = item.quantity <= item.minThreshold
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Info Column
        Column(modifier = Modifier.weight(1.5f)) {
            Text(item.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            if(isLowStock) {
                Text("Low Stock", style = MaterialTheme.typography.labelSmall, color = ErrorRed, fontWeight = FontWeight.Bold)
            } else {
                Text("In Stock", style = MaterialTheme.typography.labelSmall, color = SuccessGreen)
            }
        }
        
        Text(
            "${item.quantity} ${item.unit}", 
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        // Actions
        Row(modifier = Modifier.weight(0.8f)) {
            IconButton(onClick = { onEdit(item) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = CorporateBlue, modifier = Modifier.size(20.dp))
            }
            IconButton(onClick = { onDelete(item) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRed, modifier = Modifier.size(20.dp))
            }
        }
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha=0.3f))
}
