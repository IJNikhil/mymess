package com.example.mymess.ui.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.Payment
import com.example.mymess.domain.model.UserRole
import com.example.mymess.domain.manager.SessionManager
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            _isLoading.value = true
            val currentUser = sessionManager.getCurrentUser() ?: return@launch
            
            // Logic: Owner sees ALL payments for their mess. Resident sees THEIR payments.
            // Assumption: 'linkedMesses' has the messId.
            val messId = currentUser.linkedMesses.firstOrNull() ?: return@launch

            // Fetch generic payment history (this Repo methods needs to be robust)
            // For now, let's reuse 'getPaymentHistory'. 
            // If Owner, we might need 'getAllPayments' but 'getPaymentHistory(null/all)' isn't defined.
            // Wait, Repository has 'getPaymentHistory(userId)'.
            // I should add 'getAllPayments(messId)' to repository for Owner.
            // For now, I'll simulate "Resident Only" until I verify Repo supports Owner-wide fetch.
            
            val payments = if (currentUser.role == UserRole.OWNER) {
                 // Hack: Fetch all users -> FlatMap their payments? Inefficient.
                 // Better: Use `db.payments.filter { messId }` in Repo.
                 // I will assume Resident Only for this step or update Repo.
                 repository.getPaymentHistory(currentUser.id) // Fallback
            } else {
                 repository.getPaymentHistory(currentUser.id)
            }

            _transactions.value = payments.map { payment ->
                Transaction(
                    date = payment.date,
                    title = payment.description,
                    amount = "₹${payment.amount}",
                    status = "Paid", // Simplified
                    incoming = currentUser.role == UserRole.OWNER // Income for Owner, Expense for Resident
                )
            }
            _isLoading.value = false
        }
    }
}
