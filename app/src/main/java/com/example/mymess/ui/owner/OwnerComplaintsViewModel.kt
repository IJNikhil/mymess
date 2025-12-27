package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.Complaint
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class OwnerComplaintsUiState {
    object Loading : OwnerComplaintsUiState()
    data class Success(val complaints: List<Complaint>) : OwnerComplaintsUiState()
    data class Error(val message: String) : OwnerComplaintsUiState()
}

@HiltViewModel
class OwnerComplaintsViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<OwnerComplaintsUiState>(OwnerComplaintsUiState.Loading)
    val uiState: StateFlow<OwnerComplaintsUiState> = _uiState

    init {
        loadComplaints()
    }

    private fun loadComplaints() {
        viewModelScope.launch {
            try {
                _uiState.value = OwnerComplaintsUiState.Loading
                val allComplaints = repository.getComplaints("mess-01") // Mock Owner context
                _uiState.value = OwnerComplaintsUiState.Success(allComplaints)
            } catch (e: Exception) {
                _uiState.value = OwnerComplaintsUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun markAsResolved(complaintId: String) {
        // Mock optimization: just reload
        loadComplaints()
    }
}
