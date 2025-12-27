package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.DailyMenu
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MenuUiState {
    object Loading : MenuUiState()
    data class Success(val menu: List<DailyMenu>) : MenuUiState()
    data class Error(val message: String) : MenuUiState()
}

@HiltViewModel
class ResidentMenuViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MenuUiState>(MenuUiState.Loading)
    val uiState: StateFlow<MenuUiState> = _uiState

    init {
        loadMenu()
    }

    private fun loadMenu() {
        viewModelScope.launch {
            try {
                // Hardcoded mess-01 for demo resident
                val menu = repository.getWeeklyMenu("mess-01")
                _uiState.value = MenuUiState.Success(menu)
            } catch (e: Exception) {
                _uiState.value = MenuUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
