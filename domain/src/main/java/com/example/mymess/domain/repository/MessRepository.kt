package com.example.mymess.domain.repository

import com.example.mymess.domain.model.*
import kotlinx.coroutines.flow.Flow

interface MessRepository {
    // Auth & User Management
    suspend fun login(credentials: String): User?
    suspend fun registerUser(user: User): Boolean
    suspend fun verifyUser(userId: String): Boolean
    fun getPendingVerifications(messId: String): Flow<List<User>>
    suspend fun getAllUsers(messId: String): List<User> // Filter by mess
    suspend fun addUser(user: User): Boolean
    suspend fun updateResidentProfile(userId: String, name: String, phone: String, idProofUri: String?): Result<Boolean>

    // Mess Management
    suspend fun getAllMesses(): List<Mess>
    suspend fun getMessDetails(messId: String): Mess?

    // Resident Features
    suspend fun getResidentDashboardData(userId: String): ResidentDashboardData
    suspend fun getAttendanceHistory(userId: String): List<AttendanceEntry>
    suspend fun getPaymentHistory(userId: String): List<Payment>
    suspend fun submitPayment(payment: Payment): Boolean
    suspend fun submitFeedback(userId: String, rating: Int, comment: String): Boolean
    fun getResidents(): Flow<List<Resident>>
    
    // Services (Scoped to Mess)
    suspend fun getWeeklyMenu(messId: String): List<DailyMenu>
    suspend fun getLeaveHistory(userId: String): List<LeaveRequest>
    suspend fun submitLeaveRequest(request: LeaveRequest): Boolean
    suspend fun getComplaints(messId: String): List<Complaint>
    suspend fun submitComplaint(complaint: Complaint): Boolean

    // Owner Features
    suspend fun recordAttendance(bioUserId: String, messId: String, timestamp: Long): AttendanceEntry?
    // --- Inventory ---
    fun getInventory(): Flow<List<InventoryItem>>
    suspend fun updateInventory(item: InventoryItem): Result<Boolean>

    // --- Service Requests (Guest Meals, Complaints, etc.) ---
    fun getServiceRequests(residentId: String): Flow<List<ServiceRequest>>
    fun getServiceRequestsForOwner(status: String? = null): Flow<List<ServiceRequest>>
    suspend fun createServiceRequest(request: ServiceRequest): Result<String> // Returns ID
    suspend fun updateServiceRequest(request: ServiceRequest): Result<Boolean>
    suspend fun confirmServiceRequest(requestId: String): Result<Boolean> // Updates status + Adds Charge
    
    // V3 Reporting
    suspend fun getDailyStats(messId: String, date: Long): Map<String, Any> // Generic stats blob
    
    // Feature Mutations (V3)
    suspend fun updateDailyMenu(messId: String, dayOfWeek: String, slot: String, newContent: String): Boolean
    suspend fun addInventoryItem(messId: String, item: InventoryItem): Boolean
    suspend fun updateInventoryItem(messId: String, item: InventoryItem): Boolean // New
    suspend fun deleteInventoryItem(messId: String, itemId: String): Boolean // New
    suspend fun addExpense(messId: String, expense: Expense): Boolean
    
    // Wastage Management
    suspend fun getWastage(messId: String): List<WastageEntry>
    suspend fun logWastage(entry: WastageEntry): Boolean
    
    // Phase 21: Financial Accuracy
    suspend fun getFinancialStats(messId: String, startDate: Long, endDate: Long): FinancialReport
    
    // Owner specific
    fun getTransactions(): Flow<List<Transaction>>

    // Missing Overrides
    suspend fun updateMenu(menu: DailyMenu): Result<Boolean>
    suspend fun addResident(resident: Resident): Result<Boolean>
    suspend fun getResidentById(residentId: String): Result<Resident>
}
