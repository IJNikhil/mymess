package com.example.mymess.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mymess.ui.theme.CorporateBlue
import com.example.mymess.ui.shared.*
import com.example.mymess.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CorporateBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = { BottomSheetDefaults.DragHandle(color = MaterialTheme.colorScheme.outlineVariant) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 48.dp) // Extra padding for safety
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp).align(Alignment.CenterHorizontally)
                )
            }
            content()
        }
    }
}

@Composable
fun SheetActionButtons(
    primaryText: String,
    onPrimaryClick: () -> Unit,
    secondaryText: String = "Cancel",
    onSecondaryClick: () -> Unit,
    primaryColor: Color = CorporateBlue
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            onClick = onSecondaryClick,
            modifier = Modifier.weight(1f).height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(secondaryText)
        }
        
        Button(
            onClick = onPrimaryClick,
            modifier = Modifier.weight(1f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(primaryText)
        }
    }
}

// --- DOMAIN SHEETS ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMenuSheet(
    slotName: String,
    currentMenu: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(currentMenu) }

    CorporateBottomSheet(
        onDismissRequest = onDismiss,
        title = "Edit $slotName"
    ) {
        ModernInput(
            value = text,
            onValueChange = { text = it },
            label = "Menu Items",
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )
        
        SheetActionButtons(
            primaryText = "Save Changes",
            onPrimaryClick = { onSave(text) },
            onSecondaryClick = onDismiss
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInventorySheet(
    initialName: String = "",
    initialQty: String = "",
    initialUnit: String = "kg",
    onDismiss: () -> Unit,
    onSave: (name: String, qty: Double, unit: String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var qty by remember { mutableStateOf(initialQty) }
    var unit by remember { mutableStateOf(initialUnit) }

    val title = if(initialName.isEmpty()) "Add Inventory" else "Edit Inventory"
    val buttonText = if(initialName.isEmpty()) "Add Item" else "Update Item"

    CorporateBottomSheet(
        onDismissRequest = onDismiss,
        title = title
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ModernInput(value = name, onValueChange = { name = it }, label = "Item Name")
            ModernInput(
                value = qty, 
                onValueChange = { qty = it }, 
                label = "Quantity",
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            ModernInput(value = unit, onValueChange = { unit = it }, label = "Unit (kg, l, pcs)")
            
            SheetActionButtons(
                primaryText = buttonText,
                onPrimaryClick = {
                    val q = qty.toDoubleOrNull() ?: 0.0
                    if (name.isNotEmpty() && q > 0) {
                        onSave(name, q, unit)
                    }
                },
                onSecondaryClick = onDismiss
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseSheet(
    onDismiss: () -> Unit,
    onSave: (desc: String, amount: Double, category: String) -> Unit
) {
    var desc by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Groceries") }

    CorporateBottomSheet(
        onDismissRequest = onDismiss,
        title = "Log New Expense"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ModernInput(value = desc, onValueChange = { desc = it }, label = "Description")
            ModernInput(
                value = amount, 
                onValueChange = { amount = it }, 
                label = "Amount (₹)",
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )
            ModernInput(value = category, onValueChange = { category = it }, label = "Category")
            
            SheetActionButtons(
                primaryText = "Log Expense",
                onPrimaryClick = {
                    val a = amount.toDoubleOrNull() ?: 0.0
                    if (desc.isNotEmpty() && a > 0) {
                        onSave(desc, a, category)
                    }
                },
                onSecondaryClick = onDismiss,
                primaryColor = com.example.mymess.ui.theme.ErrorRed
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptSheet(
    onDismiss: () -> Unit,
    paymentId: String,
    date: String,
    amount: String,
    description: String,
    onShare: () -> Unit
) {
    CorporateBottomSheet(
        onDismissRequest = onDismiss,
        title = "Receipt Details"
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Receipt Card
            CorporateCard {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Transaction ID", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(paymentId, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider()
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Date", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(date, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Amount", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹$amount", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = SuccessGreen)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Description", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(description, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            SheetActionButtons(
                primaryText = "Share Receipt",
                onPrimaryClick = onShare,
                secondaryText = "Close",
                onSecondaryClick = onDismiss
            )
        }
    }
}
