package com.example.mymess.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["residentId", "date"]), 
        Index(value = ["category", "date"])
    ]
)
data class TransactionEntity(
    @PrimaryKey val id: String,
    val residentId: String?, // Nullable for Owner-level Expense/Revenue
    val type: String, // CHARGE, PAYMENT, FINE, REFUND, EXPENSE (Stored as String to avoid Enum mapping headaches initially)
    val category: String?, // PLAN_FEE, GUEST_MEAL, RAW_MATERIALS, etc.
    val amount: Double,
    val date: Long,
    val description: String?,
    val paymentMode: String? = null, // UPI, CASH, etc.
    val isExpense: Boolean = false // Helper flag for Owner Expenses
)
