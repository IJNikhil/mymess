package com.example.mymess.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.example.mymess.domain.model.Expense
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.filled.Menu

@Composable
fun OwnerExpensesScreen(
    navController: NavController,
    viewModel: OwnerExpensesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    if (showAddDialog) {
        AddExpenseDialog(
            onDismiss = { showAddDialog = false },
            onSave = { desc, amount, category ->
                viewModel.addExpense(desc, amount, category)
                showAddDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize().background(AppBackground)) {
        Column(modifier = Modifier.fillMaxSize()) {
            BackHeader(
                title = "Expenses", 
                onBack = { navController.popBackStack() }
            )

            Box(modifier = Modifier.weight(1f)) {
                when (val state = uiState) {
                    is ExpensesUiState.Loading -> {
                         Column {
                             SkeletonListRow()
                             SkeletonListRow()
                         }
                    }
                    is ExpensesUiState.Error -> Text(state.message, modifier = Modifier.align(Alignment.Center), color = ErrorRed)
                    is ExpensesUiState.Success -> {
                        ExpensesList(expenses = state.expenses)
                    }
                }
            }
        }
        
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp),
            containerColor = CorporateNavy,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun ExpensesList(expenses: List<Expense>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(expenses) { expense ->
            ExpenseItemCard(expense)
        }
    }
}

@Composable
fun ExpenseItemCard(expense: Expense) {
    val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(expense.date))
    
    CorporateCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(expense.category, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = PrimaryText)
                Text(expense.description, style = MaterialTheme.typography.bodyMedium, color = SecondaryText)
                Spacer(modifier = Modifier.height(4.dp))
                Text(date, style = MaterialTheme.typography.labelSmall, color = CorporateSlate)
            }
            Text(
                "₹${expense.amount.toInt()}", 
                style = MaterialTheme.typography.titleLarge, 
                fontWeight = FontWeight.Bold,
                color = WarningAmber
            )
        }
    }
}
