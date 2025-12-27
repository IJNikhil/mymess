package com.example.mymess.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymess.domain.model.UserRole

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val role: UserRole, // OWNER, RESIDENT
    val isVerified: Boolean = false, // For Resident Verification flow
    val profilePhotoUrl: String? = null,
    val phoneNumber: String,
    val email: String? = null,
    val passwordHash: String? = null // Simple storage for MVP
)
