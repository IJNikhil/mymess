package com.example.mymess.domain.repository

import kotlinx.coroutines.flow.Flow

interface SessionStorage {
    val authToken: Flow<String?>
    val userRole: Flow<String?> // Added
    suspend fun saveToken(token: String)
    suspend fun saveRole(role: String) // Added
    suspend fun clearToken()
}
