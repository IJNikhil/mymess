package com.example.mymess.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_master")
data class InventoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String, // VEGETABLES, GRAINS, DAIRY
    val unit: String, // KG, LITERS
    val currentQuantity: Double,
    val minThreshold: Double,
    val unitPrice: Double? = null,
    val isActive: Boolean = true
)
