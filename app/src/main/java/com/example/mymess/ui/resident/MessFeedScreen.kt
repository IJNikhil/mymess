package com.example.mymess.ui.resident

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.domain.model.DailyMenu
import com.example.mymess.domain.model.ResidentDashboardData
import com.example.mymess.domain.model.Mess
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import androidx.compose.material.icons.filled.Menu

@Composable
fun MessFeedScreen(
    navController: NavController,
    viewModel: MessFeedViewModel = hiltViewModel(),
    statsViewModel: ResidentHomeViewModel = hiltViewModel() // Injected
) {
    val uiState by viewModel.uiState.collectAsState()
    val statsState by statsViewModel.uiState.collectAsState()

    var showPaymentDialog by remember { mutableStateOf(false) }

    if (showPaymentDialog) {
        // Simple Confirmation Dialog
        AlertDialog(
            onDismissRequest = { showPaymentDialog = false },
            title = { Text("Confirm Payment") },
            text = { Text("Pay outstanding bill?") },
            confirmButton = {
                Button(onClick = {
                    val currentBill = (statsState as? ResidentHomeUiState.Success)?.dashboardData?.runningBill ?: 0.0
                    statsViewModel.payBill(currentBill)
                    showPaymentDialog = false
                }) { Text("Pay Full") }
            },
            dismissButton = {
                TextButton(onClick = { showPaymentDialog = false }) { Text("Cancel") }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = "Common Feed", 
                onBack = { navController.popBackStack() }
            )
            
            // Stats Header
            when(val stats = statsState) {
                is ResidentHomeUiState.Success -> {
                    StatsHeader(
                        data = stats.dashboardData,
                        onPayClick = { showPaymentDialog = true }
                    )
                }
                else -> {} // Loading/Error handled loosely for stats
            }
            
            when (val state = uiState) {
                is MessFeedUiState.Loading -> {
                     Column {
                         SkeletonFeedCard()
                         SkeletonFeedCard()
                     }
                }
                is MessFeedUiState.Error -> Text(state.message, color = ErrorRed, modifier = Modifier.padding(24.dp))
                is MessFeedUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(state.feed) { item ->
                            MessCard(item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatsHeader(data: ResidentDashboardData, onPayClick: () -> Unit) {
    CorporateCard(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("Current Due", style = MaterialTheme.typography.labelMedium, color = SecondaryText)
                Text("₹${data.runningBill}", style = MaterialTheme.typography.headlineMedium, color = if(data.runningBill > 0) ErrorRed else SuccessGreen, fontWeight = FontWeight.Bold)
            }
            if (data.runningBill > 0) {
                Button(
                    onClick = onPayClick, 
                    colors = ButtonDefaults.buttonColors(containerColor = CorporateBlue)
                ) {
                    Text("Pay Now")
                }
            } else {
                 Text("All Paid", color = SuccessGreen, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
             Text("Quota Left: ${data.remainingQuota} Meals", style = MaterialTheme.typography.bodyMedium, color = PrimaryText)
        }
    }
}

@Composable
fun MessCard(item: MessFeedItem) {
    CorporateCard {
        Column {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Column {
                    Text(item.mess.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = PrimaryText)
                    Text(item.mess.address, style = MaterialTheme.typography.bodyMedium, color = SecondaryText)
                }
                Icon(Icons.Default.RestaurantMenu, null, tint = CorporateBlue)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = CorporateLightGray)
            Spacer(modifier = Modifier.height(16.dp))
            
            if (item.todaysMenu != null) {
                Text("TODAY'S SPECIAL", style = MaterialTheme.typography.labelSmall, color = CorporateSlate, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Lunch", style = MaterialTheme.typography.labelMedium, color = CorporateBlue)
                        Text(item.todaysMenu.lunch, style = MaterialTheme.typography.bodyMedium, color = PrimaryText)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Dinner", style = MaterialTheme.typography.labelMedium, color = CorporateBlue)
                        Text(item.todaysMenu.dinner, style = MaterialTheme.typography.bodyMedium, color = PrimaryText)
                    }
                }
            } else {
                Text("Menu not available", style = MaterialTheme.typography.bodyMedium, color = SecondaryText)
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            CorporateButton(text = "View Details", onClick = { /* Feature for future update */ })
        }
    }
}
