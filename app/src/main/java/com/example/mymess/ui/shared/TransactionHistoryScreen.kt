package com.example.mymess.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mymess.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    navController: NavController,
    viewModel: TransactionHistoryViewModel = hiltViewModel()
) {
    // State from ViewModel
    val transactions by viewModel.transactions.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Local UI State
    var selectedTransaction by remember { mutableStateOf<Transaction?>(null) }
    var showReceipt by remember { mutableStateOf(false) }
    
    // Filters
    val filters = listOf("All", "Incoming", "Outgoing")
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredTransactions = remember(transactions, selectedFilter) {
        if (selectedFilter == "All") transactions
        else transactions.filter { 
            if (selectedFilter == "Incoming") it.incoming else !it.incoming 
        }
    }

    if (showReceipt && selectedTransaction != null) {
        ReceiptSheet(
            title = selectedTransaction!!.title,
            amount = selectedTransaction!!.amount,
            date = selectedTransaction!!.date,
            transactionId = "TXN-${selectedTransaction!!.date.hashCode()}",
            onDismiss = { showReceipt = false },
            onDownload = { /* Simulate Download */ }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(title = "Transaction History", onBack = { navController.popBackStack() })

            // Filter Chips
            Row(modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                filters.forEach { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            if(isLoading) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                 }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (filteredTransactions.isEmpty()) {
                        item {
                            Text(
                                "No records found.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.fillMaxWidth().padding(32.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                    items(filteredTransactions) { item ->
                        TransactionListItem(item) {
                            if (item.incoming) {
                                selectedTransaction = item
                                showReceipt = true
                            }
                        }
                    }
                }
            }
        }
    }
}

// Ensure this Data Class is visible to ViewModel (same package/file)
data class Transaction(val date: String, val title: String, val amount: String, val status: String, val incoming: Boolean)

@Composable
fun TransactionListItem(item: Transaction, onClick: () -> Unit = {}) {
    val isCredit = item.incoming
    val amountColor = if (isCredit) SuccessGreen else ErrorRed
    val prefix = if (isCredit) "+" else "-"
    
    CorporateCard(modifier = Modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon & Details
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                 Box(
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(amountColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if(isCredit) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward, 
                        contentDescription = null, 
                        tint = amountColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(item.title, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                    Text(item.date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    if(isCredit) Text("View Receipt", style = MaterialTheme.typography.labelSmall, color = CorporateBlue)
                }
            }
            
            // Amount
            Text(
                "$prefix ${item.amount}", 
                style = MaterialTheme.typography.titleMedium, 
                fontWeight = FontWeight.Bold, 
                color = amountColor
            )
        }
    }
}
