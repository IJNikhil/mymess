package com.example.mymess.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "residents",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"], unique = true)]
)
data class ResidentEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val roomNumber: String,
    val planId: String,
    val status: String, // ACTIVE, PENDING (Registration Status) as String for flexibility now
    val balance: Double = 0.0,
    val idProofUrl: String? = null, // Link to ID Proof image/file
    val joinDate: Long = System.currentTimeMillis()
)
