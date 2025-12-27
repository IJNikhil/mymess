package com.example.mymess.domain.manager
// Force Rebuild

import com.example.mymess.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

import com.example.mymess.domain.repository.SessionStorage

@Singleton
class SessionManager @Inject constructor(
    private val sessionStorage: SessionStorage
) {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    suspend fun startSession(user: User) {
        _currentUser.value = user
        sessionStorage.saveToken(user.id)
    }

    suspend fun endSession() {
        _currentUser.value = null
        sessionStorage.clearToken()
    }

    fun getCurrentUser(): User? {
        return _currentUser.value
    }

    fun isLoggedIn(): Boolean {
        return _currentUser.value != null
    }

    // Security Check: Throws exception if verified user is not present
    fun requireUser(): User {
        val user = _currentUser.value ?: throw SecurityException("No active session")
        if (user.pendingVerification) throw SecurityException("User pending verification")
        return user
    }
}
