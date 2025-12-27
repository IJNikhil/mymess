package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.User
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class BiometricUiState {
    object Idle : BiometricUiState()
    object Scanning : BiometricUiState()
    data class Success(val user: User, val timestamp: Long) : BiometricUiState()
    data class Failure(val message: String) : BiometricUiState()
}

@HiltViewModel
class OwnerBiometricViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<BiometricUiState>(BiometricUiState.Idle)
    val uiState: StateFlow<BiometricUiState> = _uiState
    
    // Demo: We fetch all users to simulate "Fingerprint Match" against a database
    private var allUsers: List<User> = emptyList()

    init {
        viewModelScope.launch {
            allUsers = repository.getAllUsers("mess-01") // Mock current mess context
        }
    }

    fun simulateScan() {
        viewModelScope.launch {
            _uiState.value = BiometricUiState.Scanning
            delay(1500) // Realistic "Scanning..." delay
            
            // Randomly pick a user to simulate a match
            val randomUser = allUsers.randomOrNull()
            
            if (randomUser != null) {
                // Record
                val now = System.currentTimeMillis()
                repository.recordAttendance(randomUser.id, "mess-01", now)
                _uiState.value = BiometricUiState.Success(randomUser, now)
                
                // Reset after 3 seconds
                delay(3000)
                _uiState.value = BiometricUiState.Idle
            } else {
                _uiState.value = BiometricUiState.Failure("No Match Found")
                delay(2000)
                _uiState.value = BiometricUiState.Idle
            }
        }
    }
}
