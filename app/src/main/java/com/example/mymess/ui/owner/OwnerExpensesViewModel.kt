package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.Expense
import com.example.mymess.domain.repository.MessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ExpensesUiState {
    object Loading : ExpensesUiState()
    data class Success(val expenses: List<Expense>) : ExpensesUiState()
    data class Error(val message: String) : ExpensesUiState()
}

@HiltViewModel
class OwnerExpensesViewModel @Inject constructor(
    private val repository: MessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpensesUiState>(ExpensesUiState.Loading)
    val uiState: StateFlow<ExpensesUiState> = _uiState

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            try {
                _uiState.value = ExpensesUiState.Loading
                repository.getTransactions().collect { transactions ->
                   val expenses = transactions.filter { it.amount < 0 }.map { 
                       Expense(
                           id = it.id,
                           messId = "mess-01",
                           category = it.category,
                           amount = if(it.amount < 0) -it.amount else it.amount,
                           date = it.date,
                           description = it.description
                       )
                   }
                   _uiState.value = ExpensesUiState.Success(expenses)
                }
            } catch (e: Exception) {
                _uiState.value = ExpensesUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addExpense(desc: String, amount: Double, category: String) {
        viewModelScope.launch {
            val expense = Expense(
                id = "exp-${System.currentTimeMillis()}",
                messId = "mess-01",
                amount = amount,
                category = category,
                description = desc,
                date = System.currentTimeMillis()
            )
            repository.addExpense("mess-01", expense)
            // loadExpenses() // Flow updates automatically
        }
    }
}
