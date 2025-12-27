package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign // Broadcast Icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OwnerFeesScreen(navController: NavController) {
    var filter by remember { mutableStateOf("Pending") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // FETCH REAL DATA (Simulated Repository Call)
    // In strict architecture, this should be in a ViewModel, but existing pattern uses direct data in some places or simplified VM.
    // We will access HardcodedData directly for the "LocalDemo" requirement.
    val allPayments = com.example.mymess.data.source.HardcodedData.payments
    val allUsers = com.example.mymess.data.source.HardcodedData.users
    
    // Receipt Dialog State
    var showReceiptFor by remember { mutableStateOf<com.example.mymess.domain.model.Payment?>(null) }

        if (showReceiptFor != null) {
        val payment = showReceiptFor!!
        ReceiptSheet(
            onDismiss = { showReceiptFor = null },
            paymentId = payment.id,
            date = payment.date,
            amount = payment.amount.toString(),
            description = payment.description,
            onShare = {
                scope.launch { snackbarHostState.showSnackbar("Receipt Shared!") }
                showReceiptFor = null
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            if(filter == "Pending") {
                ExtendedFloatingActionButton(
                    text = { Text("Broadcast Reminder") },
                    icon = { Icon(Icons.Default.Campaign, contentDescription = null) },
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("✓ Reminders queued for all pending residents")
                        }
                    },
                    containerColor = CorporateBlue,
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding).background(MaterialTheme.colorScheme.background)) {
            Column(modifier = Modifier.fillMaxSize()) {
                BackHeader(
                    title = "Fees Tracker", 
                    onBack = { navController.popBackStack() }
                )
                
                // Stats Row with Gradient-like feel
                val collected = allPayments.sumOf { it.amount }
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(title = "Coll. Total", value = "₹ ${collected.toInt()}", color = SuccessGreen, modifier = Modifier.weight(1f))
                    StatCard(title = "Pending Dues", value = "₹ 45k", color = ErrorRed, modifier = Modifier.weight(1f))
                }
                
                // Modern Filter Tabs
                Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                    FilterTab("Pending", filter == "Pending") { filter = "Pending" }
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterTab("Paid", filter == "Paid") { filter = "Paid" }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                     item {
                         // Colorful Table Header
                         Row(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha=0.4f))
                                 .padding(horizontal = 24.dp, vertical = 12.dp)
                         ) {
                             Text("RESIDENT", modifier = Modifier.weight(2f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                             Text("AMOUNT", modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                             Text("ACTION", modifier = Modifier.weight(1.2f), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                         }
                     }
                     
                     if (filter == "Paid") {
                         items(allPayments) { payment ->
                             val userName = allUsers.find { it.id == payment.userId }?.name ?: payment.userId
                             FeesRowItem(userName, payment.amount.toString(), "View Receipt", false) {
                                 showReceiptFor = payment
                             }
                         }
                     } else {
                         // Mock Pending (Since we don't have explicit Pending structure yet, retain mock for pending)
                         val pendingData = listOf(
                             Triple("Rahul Sharma", "3000", "Remind"),
                             Triple("Vikram Singh", "4500", "Remind"),
                             Triple("Arjun K", "1200", "Remind")
                         )
                         items(pendingData) { (name, amt, action) ->
                             FeesRowItem(name, amt, action, true) {
                                 scope.launch { snackbarHostState.showSnackbar("Reminder sent to $name") }
                             }
                         }
                     }
                }
            }
        }
    }
}

@Composable
fun FeesRowItem(name: String, amt: String, action: String, isPending: Boolean, onAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { /* Detail View? */ }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Text("Room 101", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        
        Text(
            "₹$amt", 
            modifier = Modifier.weight(1f), 
            style = MaterialTheme.typography.bodyMedium, 
            fontWeight = FontWeight.Bold, 
            color = if(isPending) ErrorRed else SuccessGreen
        )
        
        // Action Button replacing Text Link
        if (isPending) {
            OutlinedButton(
                onClick = onAction,
                modifier = Modifier.weight(1.2f).height(36.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
            ) {
                Text(action, style = MaterialTheme.typography.labelSmall)
            }
        } else {
             OutlinedButton(
                onClick = onAction,
                modifier = Modifier.weight(1.2f).height(36.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CorporateBlue),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CorporateBlue)
            ) {
                Text(action, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha=0.3f))
}

@Composable
fun StatCard(title: String, value: String, color: Color, modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Slight lift
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f)) // Colorful border
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = color)
        }
    }
}
