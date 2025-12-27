package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.DailyMenu
import com.example.mymess.domain.model.Mess
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MessFeedItem(
    val mess: Mess,
    val todaysMenu: DailyMenu?,
    val isSubscribed: Boolean
)

sealed class MessFeedUiState {
    object Loading : MessFeedUiState()
    data class Success(val feed: List<MessFeedItem>) : MessFeedUiState()
    data class Error(val message: String) : MessFeedUiState()
}

@HiltViewModel
class MessFeedViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MessFeedUiState>(MessFeedUiState.Loading)
    val uiState: StateFlow<MessFeedUiState> = _uiState

    init {
        loadFeed()
    }

    private fun loadFeed() {
        viewModelScope.launch {
            try {
                // For demo: Fetch All Messes and their Today's Menu
                // In real app, filter by User's location or subscription
                _uiState.value = MessFeedUiState.Loading
                
                val allMesses = repository.getAllMesses()
                val feedItems = allMesses.map { mess ->
                    val menus = repository.getWeeklyMenu(mess.id)
                    val today = menus.find { it.dayOfWeek == "Monday" } // Mock "Today"
                    MessFeedItem(mess, today, isSubscribed = true)
                }
                
                _uiState.value = MessFeedUiState.Success(feedItems)
            } catch (e: Exception) {
                _uiState.value = MessFeedUiState.Error(e.message ?: "Failed to load feed")
            }
        }
    }
}
