package com.example.mymess.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attendance",
    indices = [Index(value = ["residentId", "date", "mealType"], unique = true)]
)
data class AttendanceEntity(
    @PrimaryKey val id: String,
    val residentId: String,
    val date: Long,
    val mealType: String, // BREAKFAST, LUNCH, DINNER
    val status: String, // PRESENT, ABSENT, GUEST
    val isBilled: Boolean = false, // Prevents double-billing adjustments
    val markedAt: Long? = null,
    val remarks: String? = null
)
