package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.MealSlot
import com.example.mymess.domain.model.ResidentDashboardData
import com.example.mymess.domain.model.Payment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.mymess.data.source.HardcodedData
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResidentHomeViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResidentHomeUiState>(ResidentHomeUiState.Loading)
    val uiState: StateFlow<ResidentHomeUiState> = _uiState

    // In a real app, userId would be passed or retrieved from a session manager
    private val currentUserId = "res-001" 
    private val currentMessId = "mess-01"

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = ResidentHomeUiState.Loading
            try {
                val data = repository.getResidentDashboardData(currentUserId)
                val allSlots = HardcodedData.mealSlots // Get all available slots definition
                _uiState.value = ResidentHomeUiState.Success(data, allSlots)
            } catch (e: Exception) {
                _uiState.value = ResidentHomeUiState.Error(e.message ?: "Failed to load data")
            }
        }
    }

    fun payBill(amount: Double) {
        viewModelScope.launch {
            val payment = Payment(
                id = "pay-${System.currentTimeMillis()}",
                userId = currentUserId,
                messId = currentMessId,
                amount = amount,
                date = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(Date()),
                description = "Bill Payment"
            )
            repository.submitPayment(payment)
            loadDashboardData() // Refresh stats
        }
    }
}

sealed class ResidentHomeUiState {
    object Loading : ResidentHomeUiState()
    data class Success(
        val dashboardData: ResidentDashboardData,
        val availableSlots: List<MealSlot>
    ) : ResidentHomeUiState()
    data class Error(val message: String) : ResidentHomeUiState()
}
