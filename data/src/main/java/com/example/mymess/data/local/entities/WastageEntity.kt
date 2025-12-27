package com.example.mymess.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wastage")
data class WastageEntity(
    @PrimaryKey val id: String,
    val messId: String,
    val date: Long,
    val mealType: String, // Breakfast, Lunch, Dinner
    val quantityKg: Double,
    val reason: String
)
