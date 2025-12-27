package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.User
import com.example.mymess.domain.repository.MessRepository
import com.example.mymess.domain.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResidentVerificationViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _pendingUsers = MutableStateFlow<List<User>>(emptyList())
    val pendingUsers: StateFlow<List<User>> = _pendingUsers.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadPendingVerifications()
    }

    private fun loadPendingVerifications() {
        viewModelScope.launch {
            _isLoading.value = true
            val currentUser = sessionManager.getCurrentUser()
            if (currentUser != null) {
                // Assuming single mess or first linked mess
                val messId = currentUser.linkedMesses.firstOrNull() ?: "mess-01"
                repository.getPendingVerifications(messId).collect { users ->
                    _pendingUsers.value = users
                    _isLoading.value = false
                }
            } else {
                 _isLoading.value = false
            }
        }
    }

    fun verifyUser(userId: String) {
        viewModelScope.launch {
            repository.verifyUser(userId)
            // Flow will auto-update the list
        }
    }
}
