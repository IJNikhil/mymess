package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.ServiceRequest
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerServiceRequestViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _requests = MutableStateFlow<List<ServiceRequest>>(emptyList())
    val requests: StateFlow<List<ServiceRequest>> = _requests.asStateFlow()

    init {
        loadRequests()
    }

    private fun loadRequests() {
        viewModelScope.launch {
            // For MVP, we can fetch all or just pending. Repo has getServiceRequestsForOwner.
            repository.getServiceRequestsForOwner(null).collect { 
                _requests.value = it.sortedByDescending { req -> req.createdTimestamp }
            }
        }
    }

    fun updateRequestStatus(request: ServiceRequest, newStatus: String, response: String?) {
        viewModelScope.launch {
            // If quoting a price, we need to handle that. 
            // The UI passes "Price: 200" in response if it's a quote.
            // Converting that string back to Double is messy. Use specific params.
            
            var price: Double? = request.quotedPrice
            var adminNote = response
            
            if (newStatus == "ACCEPTED" && response?.startsWith("Price:") == true) {
                 val priceStr = response.substringAfter("Price:").trim()
                 price = priceStr.toDoubleOrNull()
                 adminNote = "Quoted: ₹$priceStr"
            }

            val updatedRequest = request.copy(
                status = newStatus,
                adminResponse = adminNote,
                quotedPrice = price
            )
            repository.updateServiceRequest(updatedRequest)
            // Flow auto-updates
        }
    }
}
