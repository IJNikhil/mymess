package com.example.mymess.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_requests")
data class ServiceRequestEntity(
    @PrimaryKey val id: String,
    val residentId: String,
    val type: String, // GUEST_MEAL, COMPLAINT, LEAVE
    val status: String, // PENDING, ACCEPTED, REJECTED, COMPLETED
    val title: String, // "Guest Meal - Dinner"
    val description: String,
    val requestedDate: Long,
    val quotedPrice: Double? = null,
    val adminResponse: String? = null,
    val createdTimestamp: Long = System.currentTimeMillis()
)
