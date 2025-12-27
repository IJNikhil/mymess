package com.example.mymess.data.local

import androidx.room.TypeConverter
import com.example.mymess.domain.model.UserRole

class Converters {
    @TypeConverter
    fun fromUserRole(role: UserRole): String {
        return role.name
    }

    @TypeConverter
    fun toUserRole(value: String): UserRole {
        return try {
            UserRole.valueOf(value)
        } catch (e: Exception) {
            UserRole.RESIDENT // Fallback
        }
    }
}
