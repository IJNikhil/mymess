package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.Payment
import com.example.mymess.domain.repository.MessRepository
import com.example.mymess.domain.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class GuestBookingViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _bookingState = MutableStateFlow<GuestBookingState>(GuestBookingState.Idle)
    val bookingState: StateFlow<GuestBookingState> = _bookingState.asStateFlow()

    // Exposed List of Requests
    private val _requests = MutableStateFlow<List<com.example.mymess.domain.model.ServiceRequest>>(emptyList())
    val requests: StateFlow<List<com.example.mymess.domain.model.ServiceRequest>> = _requests.asStateFlow()

    init {
        loadRequests()
    }

    private fun loadRequests() {
        viewModelScope.launch {
            val user = sessionManager.getCurrentUser()
            if (user != null) {
                repository.getServiceRequests(user.id).collect {
                    _requests.value = it
                }
            }
        }
    }

    fun bookGuestMeal(guestCount: Int, slot: String, details: String) {
        viewModelScope.launch {
            _bookingState.value = GuestBookingState.Loading
            val currentUser = sessionManager.getCurrentUser()
            if (currentUser == null) {
                _bookingState.value = GuestBookingState.Error("User not logged in")
                return@launch
            }

            val messId = currentUser.linkedMesses.firstOrNull() ?: "mess-01"
            
            // Create Service Request
            val request = com.example.mymess.domain.model.ServiceRequest(
                id = UUID.randomUUID().toString(),
                residentId = currentUser.id,
                messId = messId,
                type = "GUEST_MEAL",
                title = "$guestCount Guest(s) for $slot",
                description = details.ifEmpty { "No special notes" },
                status = "PENDING",
                requestedDate = System.currentTimeMillis()
            )
            
            val result = repository.createServiceRequest(request)
            if (result.isSuccess) {
                _bookingState.value = GuestBookingState.Success
                loadRequests() // Refresh (Flow should actually auto-update if Repo is good)
            } else {
                _bookingState.value = GuestBookingState.Error("Failed to create request")
            }
        }
    }

    fun confirmQuote(requestId: String) {
        viewModelScope.launch {
            repository.confirmServiceRequest(requestId)
            // Flow updates automatically
        }
    }
}

sealed class GuestBookingState {
    object Idle : GuestBookingState()
    object Loading : GuestBookingState()
    object Success : GuestBookingState()
    data class Error(val message: String) : GuestBookingState()
}
