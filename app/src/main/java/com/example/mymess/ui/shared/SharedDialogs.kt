// Retaining generic dialogs for now if needed, but we will move logic to SharedSheets.kt
// Use the new Sheet components in SharedSheets.kt for Phase 26
package com.example.mymess.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// Deprecated: Use EditMenuSheet instead
@Composable
fun EditMenuDialog(
    slotName: String, // Breakfast, Lunch, etc.
    currentMenu: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var text by remember { mutableStateOf(currentMenu) }

    CorporateDialog(
        onDismissRequest = onDismiss,
        title = "Edit $slotName",
        confirmButtonText = "Save",
        onConfirm = { onSave(text) },
        onDismiss = onDismiss,
        content = {
            ModernInput(
                value = text,
                onValueChange = { text = it },
                label = "Menu Items",
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

// Deprecated: Use AddInventorySheet instead
@Composable
fun AddInventoryDialog(
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
    val buttonText = if(initialName.isEmpty()) "Add" else "Save"

    CorporateDialog(
        onDismissRequest = onDismiss,
        title = title,
        confirmButtonText = buttonText,
        onConfirm = {
            val q = qty.toDoubleOrNull() ?: 0.0
            if (name.isNotEmpty() && q > 0) {
                onSave(name, q, unit)
            }
        },
        onDismiss = onDismiss,
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ModernInput(value = name, onValueChange = { name = it }, label = "Item Name")
                ModernInput(
                    value = qty, 
                    onValueChange = { qty = it }, 
                    label = "Quantity",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                ModernInput(value = unit, onValueChange = { unit = it }, label = "Unit (kg, l, pcs)")
            }
        }
    )
}

@Composable
fun AddExpenseDialog(
    onDismiss: () -> Unit,
    onSave: (desc: String, amount: Double, category: String) -> Unit
) {
    var desc by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Groceries") }

    CorporateDialog(
        onDismissRequest = onDismiss,
        title = "Log Expense",
        confirmButtonText = "Log",
        onConfirm = {
            val a = amount.toDoubleOrNull() ?: 0.0
            if (desc.isNotEmpty() && a > 0) {
                onSave(desc, a, category)
            }
        },
        onDismiss = onDismiss,
        content = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ModernInput(value = desc, onValueChange = { desc = it }, label = "Description")
                ModernInput(
                    value = amount, 
                    onValueChange = { amount = it }, 
                    label = "Amount (₹)",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                ModernInput(value = category, onValueChange = { category = it }, label = "Category")
            }
        }
    )
}
