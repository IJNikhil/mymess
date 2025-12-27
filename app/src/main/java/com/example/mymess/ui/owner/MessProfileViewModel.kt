package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessProfileViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    fun updateMess(name: String, address: String, contact: String) {
        viewModelScope.launch {
            // Mock: Update mess-01
            // Real implementation would fetch current mess, copy, and save
            // repository.updateMess(...)
        }
    }
}
