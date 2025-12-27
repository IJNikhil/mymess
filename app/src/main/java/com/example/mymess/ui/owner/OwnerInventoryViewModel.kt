package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.InventoryItem
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class InventoryUiState {
    object Loading : InventoryUiState()
    data class Success(val items: List<InventoryItem>) : InventoryUiState()
    data class Error(val message: String) : InventoryUiState()
}

@HiltViewModel
class OwnerInventoryViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<InventoryUiState>(InventoryUiState.Loading)
    val uiState: StateFlow<InventoryUiState> = _uiState

    init {
        loadInventory()
    }

    private fun loadInventory() {
        viewModelScope.launch {
            try {
                _uiState.value = InventoryUiState.Loading
                // Demo Context: Owner of mess-01
                repository.getInventory().collect { items ->
                    _uiState.value = InventoryUiState.Success(items)
                }
            } catch (e: Exception) {
                _uiState.value = InventoryUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addItem(name: String, qty: Double, unit: String) {
        viewModelScope.launch {
            val newItem = InventoryItem(
                id = "inv-${System.currentTimeMillis()}",
                messId = "mess-01",
                name = name,
                quantity = qty,
                unit = unit,
                minThreshold = 5.0, // Default
                lastUpdated = System.currentTimeMillis()
            )
            repository.addInventoryItem("mess-01", newItem)
            // loadInventory() // Flow updates automatically
        }
    }

    fun deleteItem(itemId: String) {
        viewModelScope.launch {
            repository.deleteInventoryItem("mess-01", itemId)
            // loadInventory()
        }
    }

    fun updateItem(id: String, name: String, qty: Double, unit: String) {
        viewModelScope.launch {
            val updated = InventoryItem(
                id = id,
                messId = "mess-01",
                name = name,
                quantity = qty,
                unit = unit,
                minThreshold = 5.0, // Preserve? Using 5.0 for now or fetch original
                lastUpdated = System.currentTimeMillis()
            )
            repository.updateInventoryItem("mess-01", updated)
            // loadInventory()
        }
    }
}
