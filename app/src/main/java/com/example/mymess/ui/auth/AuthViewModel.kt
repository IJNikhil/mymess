package com.example.mymess.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.User
import com.example.mymess.domain.model.UserRole
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import com.example.mymess.domain.repository.SessionStorage
import kotlinx.coroutines.flow.first

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

// Imports moved to top

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionStorage: SessionStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            val token = sessionStorage.authToken.first()
            if (!token.isNullOrEmpty()) {
                _uiState.value = AuthUiState.Loading
                try {
                    val user = repository.login(token) // Re-login with token (ID)
                    if (user != null) {
                        _uiState.value = AuthUiState.Success(user)
                    } else {
                        // Token invalid/user deleted -> Clear
                        sessionStorage.clearToken()
                        _uiState.value = AuthUiState.Idle
                    }
                } catch (e: Exception) {
                    sessionStorage.clearToken()
                    _uiState.value = AuthUiState.Error("Session expired")
                }
            }
        }
    }

    // Simplified Login for Demo: Just User ID
    fun login(userId: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                // Determine if it's an email or ID
                val user = repository.login(userId)
                if (user != null) {
                    _uiState.value = AuthUiState.Success(user)
                    // Persist for next launch
                    sessionStorage.saveToken(user.id)
                    sessionStorage.saveRole(user.role.name)
                } else {
                    _uiState.value = AuthUiState.Error("User not found")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }

    // Safety Net for Demo: Force Login as Owner
    fun forceOwnerLogin() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                // Try repository first
                var user = repository.login("owner-101") ?: repository.login("owner@mymess.com")
                
                // If Migration Failed, Construct Manually
                if (user == null) {
                    user = User(
                        id = "owner-101", 
                        name = "Vikram Singh", 
                        role = UserRole.OWNER, 
                        designation = "Admin", 
                        email = "owner@mymess.com", // Ensure email is set
                        currentPlanId = "N/A", 
                        pendingVerification = false, 
                        linkedMesses = listOf("mess-01")
                    )
                    // Save this "rescue" user to repo? Yes, try to register/add it so it sticks.
                    repository.registerUser(user)
                }
                
                _uiState.value = AuthUiState.Success(user)
                sessionStorage.saveToken(user.id)
                sessionStorage.saveRole(user.role.name)
            } catch (e: Exception) {
               _uiState.value = AuthUiState.Error("Critical Login Error: ${e.message}")
            }
        }
    }

    fun register(name: String, email: String, phone: String, roleStr: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                // Determine mess linkage logic (For demo: Auto-link to mess-01 if Resident)
                val role = if(roleStr == "Owner") UserRole.OWNER else UserRole.RESIDENT
                val linkedMesses = if(role == UserRole.RESIDENT) listOf("mess-01") else listOf()
                
                val newUser = User(
                    id = "u-${System.currentTimeMillis()}", // Generate ID
                    name = name,
                    role = role,
                    designation = if(role == UserRole.OWNER) "Admin" else "New Member",
                    currentPlanId = "plan-001",
                    pendingVerification = true, // Requires verification
                    linkedMesses = linkedMesses,
                    email = email,
                    phone = phone
                )
                
                val success = repository.registerUser(newUser)
                if (success) {
                    // Auto-login after register? Or just success state.
                    // Let's auto-save token/role for convenience
                    sessionStorage.saveToken(newUser.id)
                    sessionStorage.saveRole(role.name)
                    _uiState.value = AuthUiState.Success(newUser)
                } else {
                    _uiState.value = AuthUiState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Error")
            }
        }
    }
    
    // New checkSession returning destination
    suspend fun checkSession(): String {
        val token = sessionStorage.authToken.first()
        val role = sessionStorage.userRole.first()
        
        return if (!token.isNullOrEmpty() && !role.isNullOrEmpty()) {
            if (role == UserRole.OWNER.name) "owner_hub" else "resident_hub"
        } else {
            "login"
        }
    }
    
    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            sessionStorage.clearToken()
            _uiState.value = AuthUiState.Idle
            onComplete()
        }
    }
}
