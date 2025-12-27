package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.LeaveRequest
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LeavesUiState {
    object Loading : LeavesUiState()
    data class Success(val history: List<LeaveRequest>) : LeavesUiState()
    data class Error(val message: String) : LeavesUiState()
}

@HiltViewModel
class ResidentLeavesViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LeavesUiState>(LeavesUiState.Loading)
    val uiState: StateFlow<LeavesUiState> = _uiState

    init {
        loadLeaves()
    }

    private fun loadLeaves() {
        viewModelScope.launch {
            // Hardcoded user for demo
            val leaves = repository.getLeaveHistory("res-001")
            _uiState.value = LeavesUiState.Success(leaves)
        }
    }

    fun applyForLeave(start: Long, end: Long, reason: String) {
        viewModelScope.launch {
            val req = LeaveRequest(
                id = "lv-${System.currentTimeMillis()}",
                userId = "res-001",
                messId = "mess-01",
                startDate = start,
                endDate = end,
                reason = reason,
                status = "PENDING"
            )
            repository.submitLeaveRequest(req)
            loadLeaves() // Refresh
        }
    }
}
