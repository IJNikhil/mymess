package com.example.mymess.ui.resident

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.Complaint
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: Long
)

sealed class ChatUiState {
    object Loading : ChatUiState()
    data class Success(val messages: List<ChatMessage>) : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}

@HiltViewModel
class ResidentComplaintsViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState

    init {
        loadChat()
    }

    private fun loadChat() {
        viewModelScope.launch {
            try {
                // Hardcoded: mess-01, res-001 (Should get from SessionManager really)
                val allComplaints = repository.getComplaints("mess-01")
                val userComplaints = allComplaints.filter { it.userId == "res-001" }

                val chatStream = mutableListOf<ChatMessage>()
                
                // Greeting
                chatStream.add(ChatMessage("sys-init", "Hello! How can we help you today?", false, System.currentTimeMillis()))

                userComplaints.sortedBy { it.filedDate }.forEach { tkt ->
                    // User Message
                    chatStream.add(ChatMessage(tkt.id + "_u", tkt.description, true, tkt.filedDate))
                    
                    // System Auto-Reply (Simulation)
                    val replyText = "We received your ticket '${tkt.title}'. Status: ${tkt.status}"
                    chatStream.add(ChatMessage(tkt.id + "_s", replyText, false, tkt.filedDate + 1000))
                }

                _uiState.value = ChatUiState.Success(chatStream)
            } catch (e: Exception) {
                _uiState.value = ChatUiState.Error(e.message ?: "Failed to load chat")
            }
        }
    }

    fun sendMessage(text: String) {
        viewModelScope.launch {
            if (text.isBlank()) return@launch
            
            val complaint = Complaint(
                id = "msg-${System.currentTimeMillis()}",
                userId = "res-001",
                messId = "mess-01",
                title = "Chat Message",
                description = text,
                status = "OPEN",
                filedDate = System.currentTimeMillis()
            )
            repository.submitComplaint(complaint)
            loadChat()
        }
    }
}

