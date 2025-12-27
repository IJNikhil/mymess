package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.Payment
import com.example.mymess.domain.model.Resident
import com.example.mymess.domain.repository.MessRepository
import com.example.mymess.domain.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ResidentPaymentViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Loading)
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    private val _resident = MutableStateFlow<Resident?>(null)
    val resident: StateFlow<Resident?> = _resident.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val user = sessionManager.getCurrentUser()
            if (user != null) {
                // Fetch Resident to get Balance
                val resResult = repository.getResidentById(user.id) // Assuming ID match
                // Note: Repo usually returns Entity converted to Model. 
                // Error handling skipped for MVP speed.
                if (resResult.isSuccess) {
                    _resident.value = resResult.getOrNull()
                    _uiState.value = PaymentUiState.Idle
                } else {
                    _uiState.value = PaymentUiState.Error("Failed to fetch bill details")
                }
            }
        }
    }

    fun submitPayment(amount: Double, method: String) {
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Processing
            val currentRes = _resident.value ?: return@launch

            val payment = Payment(
                id = UUID.randomUUID().toString(),
                userId = currentRes.userId,
                messId = "mess-01",
                amount = amount,
                date = System.currentTimeMillis().toString(),
                description = "Bill Payment via $method"
            )

            val success = repository.submitPayment(payment)
            if (success) {
                _uiState.value = PaymentUiState.Success
                loadData() // Refresh Balance
            } else {
                _uiState.value = PaymentUiState.Error("Payment Failed")
            }
        }
    }
}

sealed class PaymentUiState {
    object Loading : PaymentUiState()
    object Idle : PaymentUiState()
    object Processing : PaymentUiState()
    object Success : PaymentUiState()
    data class Error(val message: String) : PaymentUiState()
}
