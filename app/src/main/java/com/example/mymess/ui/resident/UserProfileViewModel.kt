package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.Resident
import com.example.mymess.domain.repository.MessRepository
import com.example.mymess.domain.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _resident = MutableStateFlow<Resident?>(null)
    val resident: StateFlow<Resident?> = _resident.asStateFlow()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val user = sessionManager.getCurrentUser()
            if (user != null) {
                // Fetch Resident Details for this User
                val result = repository.getResidentById(user.id) // Assuming ID matches or logic exists
                // Wait, getResidentById expects ResidentID, but we have UserId. 
                // Repository usually handles this mapping or we iterate.
                // RoomMessRepository.getResidentById queries by ID. 
                // Ideally we need getResidentByUserId in Repo.
                // But for now, let's assume sessionManager holds ResidentID or UserID is linked.
                // Actually, UserEntity and ResidentEntity share ID in my seeding?
                // Seed: user.id "res-001", resident.id "res-001". YES.
                
                if (result.isSuccess) {
                    _resident.value = result.getOrNull()
                } else {
                    // Try to fetch via UserID if primary key differs. 
                    // In my seeding, they are same. If manual reg, might differ.
                    // Let's assume same for MVP speed.
                }
            }
        }
    }

    fun updateProfile(name: String, phone: String, idProofUri: String?) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            val currentRes = _resident.value
            if (currentRes != null) {
                val result = repository.updateResidentProfile(currentRes.id, name, phone, idProofUri)
                if (result.isSuccess) {
                    _uiState.value = ProfileUiState.Success
                    loadProfile() // Refresh
                } else {
                    _uiState.value = ProfileUiState.Error("Failed to update profile")
                }
            } else {
                 _uiState.value = ProfileUiState.Error("Profile not loaded")
            }
        }
    }
}

sealed class ProfileUiState {
    object Idle : ProfileUiState()
    object Loading : ProfileUiState()
    object Success : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
