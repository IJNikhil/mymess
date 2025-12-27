package com.example.mymess.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymess.domain.model.FinancialReport
import com.example.mymess.domain.repository.MessRepository
import com.example.mymess.domain.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class OwnerReportsViewModel @Inject constructor(
    private val repository: MessRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _reportState = MutableStateFlow(
        FinancialReport(
            id = "", 
            messId = "", 
            month = "", 
            revenue = 0.0, 
            expenses = 0.0, 
            profit = 0.0, 
            generatedDate = 0L
        )
    )
    val reportState: StateFlow<FinancialReport> = _reportState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Default: Monthly Report
        loadReport("Monthly")
    }

    fun loadReport(filter: String, customStart: Long = 0L, customEnd: Long = 0L) {
        viewModelScope.launch {
            _isLoading.value = true
            val currentUser = sessionManager.getCurrentUser()
            if (currentUser == null) {
                _isLoading.value = false
                return@launch
            }
            
            val messId = currentUser.linkedMesses.firstOrNull() ?: return@launch
            
            val (start, end) = when(filter) {
                "Daily" -> getTodayRange()
                "Weekly" -> getWeekRange()
                "Monthly" -> getMonthRange()
                "Annually" -> getYearRange()
                "Custom" -> Pair(customStart, customEnd)
                else -> getMonthRange()
            }

            // Real Data Call
            val report = repository.getFinancialStats(messId, start, end)
            _reportState.value = report
            _isLoading.value = false
        }
    }

    private fun getTodayRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val start = calendar.timeInMillis
        
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis
        return start to end
    }

    private fun getWeekRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        val start = calendar.timeInMillis
        val end = System.currentTimeMillis()
        return start to end
    }

    private fun getMonthRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        val start = calendar.timeInMillis
        val end = System.currentTimeMillis()
        return start to end
    }

    private fun getYearRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        val start = calendar.timeInMillis
        val end = System.currentTimeMillis()
        return start to end
    }
}
