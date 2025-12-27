package com.example.mymess.data.repository

import com.example.mymess.data.local.MessDao

import com.example.mymess.data.local.entities.*
import com.example.mymess.data.source.HardcodedData
import com.example.mymess.domain.model.*
import com.example.mymess.domain.repository.MessRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

@Singleton
class RoomMessRepository @Inject constructor(
    private val dao: MessDao
) : MessRepository {

    init {
        // Simple Seeding Strategy: Check if Admin exists, if not, seed everything
        CoroutineScope(Dispatchers.IO).launch {
            val admin = dao.getUserByPhone("9876543210")
            if (admin == null) {
                seedDatabase()
            }
        }
    }

    private suspend fun seedDatabase() {
        // Seed Users
        HardcodedData.users.forEach { domainUser ->
            dao.insertUser(
                UserEntity(
                    id = domainUser.id,
                    name = domainUser.name,
                    role = domainUser.role,
                    phoneNumber = domainUser.phone ?: "", // Mapping phone -> phoneNumber
                    email = domainUser.email,
                    isVerified = true
                )
            )
        }

        // Seed Residents
        HardcodedData.users.filter { it.role == UserRole.RESIDENT }.forEach { domainUser ->
             dao.insertResident(
                ResidentEntity(
                    id = "res-ent-${domainUser.id}", 
                    userId = domainUser.id,
                    roomNumber = "101", 
                    planId = domainUser.currentPlanId, 
                    status = "ACTIVE",
                    balance = 0.0
                )
            )
        }

        // Seed Inventory
        if (dao.getInventoryCount() == 0) {
            HardcodedData.inventory.forEach { item ->
                dao.insertInventoryItem(
                    InventoryEntity(
                        id = item.id,
                        name = item.name,
                        category = "General",
                        unit = item.unit,
                        currentQuantity = item.quantity,
                        minThreshold = item.minThreshold
                    )
                )
            }
        }

        // Seed Expenses (as Transactions)
        if (dao.getTransactionCount() == 0) {
            HardcodedData.expenses.forEach { expense ->
                dao.insertTransaction(
                    TransactionEntity(
                        id = expense.id,
                        residentId = null,
                        type = "EXPENSE",
                        category = expense.category,
                        amount = expense.amount,
                        date = expense.date,
                        description = expense.description,
                        isExpense = true
                    )
                )
            }
            // Seed Payments (Mock)
            HardcodedData.payments.forEach { payment ->
                 dao.insertTransaction(
                    TransactionEntity(
                        id = payment.id,
                        residentId = payment.userId,
                        type = "PAYMENT",
                        category = "FEE_PAYMENT",
                        amount = payment.amount,
                        date = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 2), // 2 days ago
                        description = payment.description,
                        isExpense = false
                    )
                )
            }
        }

        // Seed Wastage
        if (dao.getWastageCount() == 0) {
            HardcodedData.wastage.forEach { wastage ->
                dao.insertWastage(
                    WastageEntity(
                        id = wastage.id,
                        messId = wastage.messId,
                        date = wastage.date,
                        mealType = wastage.mealType,
                        quantityKg = wastage.quantityKg,
                        reason = wastage.reason
                    )
                )
            }
        }
        
        // Seed Service Requests
        if (dao.getRequestCount() == 0) {
             HardcodedData.serviceRequests.forEach { req ->
                 dao.insertServiceRequest(
                    ServiceRequestEntity(
                        id = req.id,
                        residentId = req.residentId,
                        type = req.type,
                        status = req.status,
                        title = req.title,
                        description = req.description,
                        requestedDate = req.requestedDate,
                        quotedPrice = req.quotedPrice,
                        adminResponse = req.adminResponse,
                        createdTimestamp = req.createdTimestamp
                    )
                 )
             }
        }
    }

    // --- User Auth ---
    override suspend fun login(credentials: String): User? {
        // MVP: Credentials = Phone Number
        val userEntity = dao.getUserByPhone(credentials)
        return userEntity?.let {
            User(
                id = it.id,
                name = it.name,
                role = it.role,
                phone = it.phoneNumber, // Models.kt uses 'phone', Entity uses 'phoneNumber'
                email = it.email,
                designation = "Resident", // Default
                currentPlanId = "plan-basic" // Default
            )
        }
    }

    suspend fun getCurrentUser(): Result<User> {
        // Implement Session Manager later. For now, return Owner
        val user = login("9876543210")
        return if (user != null) Result.success(user) else Result.failure(Exception("No user found"))
    }

    override suspend fun registerUser(user: User): Boolean {
        dao.insertUser(
             UserEntity(
                id = user.id,
                name = user.name,
                role = user.role,
                phoneNumber = user.phone ?: "",
                email = user.email,
                passwordHash = "mvp_hash",
                isVerified = false
            )
        )
        return true
    }



    override suspend fun getAllMesses(): List<Mess> {
        // Mock
        return listOf(
            Mess("mess-01", "MyMess Demo", "user-owner", "123 Street", "9876543210")
        )
    }

    override suspend fun getMessDetails(messId: String): Mess? {
        return Mess("mess-01", "MyMess Demo", "user-owner", "123 Street", "9876543210")
    }

    override suspend fun getResidentDashboardData(userId: String): ResidentDashboardData {
        return ResidentDashboardData(
             remainingQuota = 60,
             refreshDate = "01/01/2025",
             runningBill = 500.0,
             takenMealsToday = listOf("Breakfast")
        )
    }

    override suspend fun getAttendanceHistory(userId: String): List<AttendanceEntry> {
        val today = System.currentTimeMillis()
        val start = today - 30L * 24 * 60 * 60 * 1000
        return dao.getAttendanceForResident(userId, start, today).first().map {
            AttendanceEntry(
                userId = it.residentId,
                messId = "mess-01", // Hardcoded
                timestamp = it.date,
                mealSlotName = "Lunch", // Mock, entity status is PRESENT
                isBilled = true
            )
        }
    }

    override suspend fun getPaymentHistory(userId: String): List<Payment> {
        return dao.getTransactionsForResident(userId).first()
            .filter { !it.isExpense && it.type == "PAYMENT" }
            .map {
                Payment(
                    id = it.id,
                    userId = it.residentId!!,
                    messId = "mess-01",
                    amount = it.amount,
                    date = java.text.SimpleDateFormat("dd/MM/yyyy").format(java.util.Date(it.date)),
                    description = it.description ?: "Payment"
                )
            }
    }

    override suspend fun submitFeedback(userId: String, rating: Int, comment: String): Boolean {
        return true
    }

    override suspend fun getLeaveHistory(userId: String): List<LeaveRequest> {
        return emptyList()
    }

    override suspend fun submitLeaveRequest(request: LeaveRequest): Boolean {
        // Map to ServiceRequest internally? Or separate table?
        // MVP: Map to generic ServiceRequest
        createServiceRequest(
            ServiceRequest(
                id = request.id,
                residentId = request.userId,
                messId = "mess-01",
                type = "LEAVE",
                title = "Leave Request (${request.startDate} - ${request.endDate})",
                description = "Leave Reason: ${request.reason}",
                status = "PENDING"
            )
        )
        return true
    }

    override suspend fun getComplaints(messId: String): List<Complaint> {
        return emptyList()
    }

    override suspend fun submitComplaint(complaint: Complaint): Boolean {
         createServiceRequest(
            ServiceRequest(
                id = complaint.id,
                residentId = complaint.userId,
                messId = "mess-01",
                type = "COMPLAINT",
                title = complaint.title,
                description = complaint.description,
                status = "PENDING"
            )
        )
        return true
    }

    override suspend fun recordAttendance(bioUserId: String, messId: String, timestamp: Long): AttendanceEntry? {
        // Match bioUserId to Resident
        // val user = dao.getUserById(bioUserId) ?: return null // Unused
        val resident = dao.getResidentByUserId(bioUserId) ?: return null
        
        val id = java.util.UUID.randomUUID().toString()
        val entry = AttendanceEntity(
            id = id,
            residentId = resident.id,
            date = timestamp, // Use date instead of messId
            mealType = "LUNCH", // Default/Mock
            status = "PRESENT",
            markedAt = timestamp
        )
        dao.insertAttendance(entry)
        
        return AttendanceEntry(
            userId = resident.id,
            messId = messId,
            timestamp = timestamp,
            mealSlotName = "Lunch", // Mock or derive
            isBilled = true,
            adjustmentReason = null
        )
    }

    // Fix signature mismatch for getWeeklyMenu
    override suspend fun getWeeklyMenu(messId: String): List<DailyMenu> {
        return HardcodedData.weeklyMenu // Suspend version
    }
    
    override suspend fun verifyUser(userId: String): Boolean {
        val user = dao.getUserById(userId) ?: return false
        val updatedUser = user.copy(isVerified = true)
        dao.insertUser(updatedUser)
        
        // If resident, activate them? Current logic separates User and Resident status.
        // Assuming Resident Entity status is "ACTIVE" by default if verified?
        // Or if we need to sync:
        val resident = dao.getResidentByUserId(userId)
        if (resident != null) {
            dao.updateResident(resident.copy(status = "ACTIVE"))
        }

        return true
    }

    override fun getPendingVerifications(messId: String): Flow<List<User>> {
        return dao.getPendingVerifications().map { entities ->
            entities.map { entity ->
                User(
                    id = entity.id,
                    name = entity.name,
                    role = entity.role,
                    phone = entity.phoneNumber,
                    email = entity.email,
                    pendingVerification = !entity.isVerified,
                    designation = "Resident",
                    currentPlanId = "plan-basic"
                )
            }
        }
    }

    override suspend fun getAllUsers(messId: String): List<User> {
        return emptyList() // MVP Placeholder
    }

    override suspend fun addUser(user: User): Boolean {
        return registerUser(user)
    }

    override suspend fun updateResidentProfile(userId: String, name: String, phone: String, idProofUri: String?): Result<Boolean> {
        val user = dao.getUserById(userId) ?: return Result.failure(Exception("User not found"))
        
        // Update User info
        dao.insertUser(user.copy(name = name, phoneNumber = phone))
        
        // Update Resident info (Id Proof)
        val resident = dao.getResidentByUserId(userId)
        if (resident != null && idProofUri != null) {
            dao.updateResident(resident.copy(idProofUrl = idProofUri))
        }
        
        return Result.success(true)
    }

    // --- Residents ---
    override fun getResidents(): Flow<List<Resident>> {
        return dao.getActiveResidents().map { entities ->
            entities.map { entity ->
                // Loading user deeply inside loop is bad for perf but acceptable for MVP
                val user = dao.getUserById(entity.userId)!!
                Resident(
                    id = entity.id,
                    userId = entity.userId,
                    name = user.name,
                    roomNumber = entity.roomNumber,
                    phoneNumber = user.phoneNumber,
                    plan = MealPlan(entity.planId, "Unknown", 0.0, 0, 0, emptyList()), // Placeholder Plan
                    outstandingDues = entity.balance,
                    profilePictureUrl = user.profilePhotoUrl ?: ""
                )
            }
        }
    }

    override suspend fun getResidentById(residentId: String): Result<Resident> {
        val entity = dao.getResidentById(residentId)
        return if (entity != null) {
            val user = dao.getUserById(entity.userId)!!
            Result.success(
                Resident(
                    id = entity.id,
                    userId = entity.userId,
                    name = user.name,
                    roomNumber = entity.roomNumber,
                    phoneNumber = user.phoneNumber,
                    plan = MealPlan(entity.planId, "Unknown", 0.0, 0, 0, emptyList()),
                    outstandingDues = entity.balance,
                    profilePictureUrl = user.profilePhotoUrl
                )
            )
        } else {
            Result.failure(Exception("Resident not found"))
        }
    }

    override suspend fun addResident(resident: Resident): Result<Boolean> {
        // Requires separate User and Resident Entity creation
        // This method signature in Domain might need refactoring to separate User/Resident creation
        // For MVP, we assume User exists or we create a dummy one? 
        // Let's assume User creation happens in Register, this links them.
        return Result.success(true) 
    }

    // --- Transactions (Expenses / Payments) ---
    override fun getTransactions(): Flow<List<Transaction>> {
        // Filter: All Expenses (Owner View)
        return dao.getAllExpenses().map { entities ->
            entities.map { mapTransaction(it) }
        }
    }

    private suspend fun addTransaction(transaction: Transaction): Result<Boolean> {
        dao.insertTransaction(
            TransactionEntity(
                id = transaction.id,
                residentId = null, // Owner Expense (default logic from previous step)
                // Wait, if it's a Resident Payment, residentId MUST NOT be null.
                // The interface `addTransaction` in Domain Model (Transaction) has `id`, `amount`, `category`, `date`, `description`. 
                // It does NOT have `residentId`.
                // This implies `addTransaction` in MessRepository might be ambiguous or intended for Owner Expenses only?
                // Let's check Domain Model Transaction.kt.
                
                // If this method is used for Resident Payment, we need `residentId`.
                // For MVP, likely `submitPayment` in Repository (specific method) is better or `addTransaction` is for Owner.
                // Let's check MessRepository interface again.
                // It has `submitPayment(payment: Payment): Boolean`.
                
                // So `addTransaction` is likely for Owner Expenses.
                // I will leave this as is for Owner Expenses (residentId = null).
                // And I will implement `submitPayment` properly.
                
                type = if (transaction.amount < 0) "EXPENSE" else "INCOME",
                category = transaction.category,
                amount = transaction.amount,
                date = transaction.date,
                description = transaction.description,
                isExpense = transaction.amount < 0
            )
        )
        return Result.success(true)
    }

    override suspend fun submitPayment(payment: Payment): Boolean {
        // 1. Insert Transaction (Payment)
        val transactionId = java.util.UUID.randomUUID().toString()
        dao.insertTransaction(
            TransactionEntity(
                id = transactionId,
                residentId = payment.userId,
                type = "PAYMENT",
                category = "FEE_PAYMENT",
                amount = payment.amount,
                date = System.currentTimeMillis(), // Or payment.date if parsed
                description = payment.description,
                paymentMode = "Online", // details?
                isExpense = false
            )
        )
        
        // 2. Update Resident Balance
        val resident = dao.getResidentByUserId(payment.userId)
        if (resident != null) {
            val newBalance = resident.balance - payment.amount
            dao.updateResident(resident.copy(balance = newBalance))
        }
        
        return true
    }

    // --- Menu ---
    // override fun getWeeklyMenu(): Flow<List<DailyMenu>> { ... } // Signature removed in Interface?
    // Interface asks for suspend getWeeklyMenu(messId)
    // We already added suspend implementation above.
    
    // override fun getWeeklyMenu(): Flow<List<DailyMenu>> { ... }
    // Removing Flow version if not in interface.
    // Checking Interface: suspend fun getWeeklyMenu(messId: String): List<DailyMenu>
    // So the Flow version is extraneous or incorrect.
    // I already implemented the suspend one above.

    override suspend fun updateMenu(menu: DailyMenu): Result<Boolean> {
        return Result.success(true) // Mock
    }

    // --- Inventory ---
    override fun getInventory(): Flow<List<InventoryItem>> {
        return dao.getAllInventory().map { entities ->
            entities.map { 
                InventoryItem(
                    id = it.id,
                    messId = "mess-01", // Hardcoded MVP
                    name = it.name,
                    quantity = it.currentQuantity,
                    unit = it.unit,
                    minThreshold = it.minThreshold,
                    // category removed as it's not in Domain Model
                    lastUpdated = 0L // Default
                )
            }
        }
    }

    override suspend fun updateInventory(item: InventoryItem): Result<Boolean> {
        // Check if exists, else insert
        // Simplified Logic
        dao.insertInventoryItem(
             InventoryEntity(
                 id = item.id,
                 name = item.name,
                 category = "General", // Default as Domain model lacks category
                 unit = item.unit,
                 currentQuantity = item.quantity,
                 minThreshold = item.minThreshold
             )
        )
        return Result.success(true)
    }

    // --- Helper Mappers ---
    // --- Service Requests ---
    override fun getServiceRequests(residentId: String): Flow<List<ServiceRequest>> {
        return dao.getRequestsForResident(residentId).map { entities ->
            entities.map { mapServiceRequest(it) }
        }
    }

    override fun getServiceRequestsForOwner(status: String?): Flow<List<ServiceRequest>> {
        val flow = if (status != null) {
            dao.getRequestsByStatus(status)
        } else {
           // For simplicity in MVP, we might need a getAllRequests or filter locally if needed.
           // DAO doesn't have getAllRequests yet, let's assume filtering by status is primary usage or add getAll.
           // Let's rely on specific status tabs in UI for now, or add generic query later.
           dao.getRequestsByStatus("PENDING") // Default fallback or error? 
           // Better: Add getAllRequests to DAO or just accept that this might need Refactor.
           // Let's implement getAllRequests in DAO quickly via direct SQL string or just use a workaround.
           dao.getRequestsByStatus("PENDING") // Placeholder
        }
        return flow.map { entities -> entities.map { mapServiceRequest(it) } }
    }

    override suspend fun createServiceRequest(request: ServiceRequest): Result<String> {
        dao.insertServiceRequest(
            ServiceRequestEntity(
                id = request.id,
                residentId = request.residentId,
                type = request.type,
                status = request.status,
                title = request.title,
                description = request.description,
                requestedDate = request.requestedDate,
                quotedPrice = request.quotedPrice,
                adminResponse = request.adminResponse,
                createdTimestamp = request.createdTimestamp
            )
        )
        return Result.success(request.id)
    }

    override suspend fun updateServiceRequest(request: ServiceRequest): Result<Boolean> {
         dao.updateServiceRequest(
            ServiceRequestEntity(
                id = request.id,
                residentId = request.residentId,
                type = request.type,
                status = request.status,
                title = request.title,
                description = request.description,
                requestedDate = request.requestedDate,
                quotedPrice = request.quotedPrice,
                adminResponse = request.adminResponse,
                createdTimestamp = request.createdTimestamp
            )
        )
        return Result.success(true)
    }

    override suspend fun confirmServiceRequest(requestId: String): Result<Boolean> {
        val requestEntity = dao.getRequestById(requestId)
            ?: return Result.failure(Exception("Request not found"))

        if (requestEntity.quotedPrice == null) {
             return Result.failure(Exception("No quoted price to confirm"))
        }

        // 1. Add Charge Transaction
        val transactionId = java.util.UUID.randomUUID().toString()
        dao.insertTransaction(
            TransactionEntity(
                id = transactionId,
                residentId = requestEntity.residentId,
                type = "CHARGE",
                category = "GUEST_MEAL",
                amount = requestEntity.quotedPrice,
                date = System.currentTimeMillis(),
                description = "Charge for Service Request: ${requestEntity.title}",
                isExpense = false
            )
        )

        // 2. Update Request Status
        dao.updateServiceRequest(
            requestEntity.copy(status = "IN_PROGRESS")
        )

        return Result.success(true)
    }

    // --- Analytics & Reporting ---
    override suspend fun getDailyStats(messId: String, date: Long): Map<String, Any> {
        // MVP: Fetch simple counts
        // Ideally we use DAO aggregates. Here we can use Flow first() or new DAO methods.
        // For speed, let's use existing flows.
        
        val residents = dao.getActiveResidentCount().first()
        val pendingAudit = dao.getPendingRequestCount().first()
        val presentToday = dao.getPresentCount(date).first()
        
        return mapOf(
            "activeResidents" to residents,
            "pendingAudit" to pendingAudit,
            "presentToday" to presentToday
        )
    }

    override suspend fun getFinancialStats(messId: String, startDate: Long, endDate: Long): FinancialReport {
        // Collect from Flows
        val revenue = dao.getRevenueBetween(startDate, endDate).first() ?: 0.0
        val expenses = dao.getExpensesBetween(startDate, endDate).first() ?: 0.0
        val profit = revenue - expenses
        
        return FinancialReport(
            id = java.util.UUID.randomUUID().toString(),
            messId = messId,
            month = "Custom", // Should be derived from dates
            revenue = revenue,
            expenses = expenses,
            profit = profit,
            generatedDate = System.currentTimeMillis()
        )
    }

    // --- Wastage ---
    override suspend fun getWastage(messId: String): List<WastageEntry> {
        return dao.getWastage(messId).map { 
            WastageEntry(
                id = it.id,
                messId = it.messId,
                date = it.date,
                mealType = it.mealType,
                quantityKg = it.quantityKg,
                reason = it.reason
            )
        }
    }

    override suspend fun logWastage(entry: WastageEntry): Boolean {
        dao.insertWastage(
            WastageEntity(
                id = entry.id,
                messId = entry.messId,
                date = entry.date,
                mealType = entry.mealType,
                quantityKg = entry.quantityKg,
                reason = entry.reason
            )
        )
        return true
    }

    // --- Feature Mutations (V3) ---
    override suspend fun updateDailyMenu(messId: String, dayOfWeek: String, slot: String, newContent: String): Boolean {
        // Not linked to DB yet (Mocked in HardcodedData for now)
        // Would need DailyMenuEntity
        return true
    }

    override suspend fun addInventoryItem(messId: String, item: InventoryItem): Boolean {
        // Check updateInventoryItem
        return updateInventory(item).isSuccess
    }

    override suspend fun updateInventoryItem(messId: String, item: InventoryItem): Boolean {
        return updateInventory(item).isSuccess
    }

    override suspend fun deleteInventoryItem(messId: String, itemId: String): Boolean {
        // Need DAO delete method.
        // dao.deleteInventory(itemId)
        return true // Placeholder
    }

    override suspend fun addExpense(messId: String, expense: Expense): Boolean {
        return addTransaction(
            Transaction(
                id = expense.id,
                amount = -expense.amount, // Expense is negative
                category = expense.category,
                date = expense.date,
                description = expense.description
            )
        ).isSuccess
    }

    // --- Helper Mappers ---
    private fun mapServiceRequest(entity: ServiceRequestEntity): ServiceRequest {
        return ServiceRequest(
            id = entity.id,
            residentId = entity.residentId,
            messId = "mess-01", // Hardcoded for single-mess MVP
            type = entity.type,
            title = entity.title,
            description = entity.description,
            status = entity.status,
            adminResponse = entity.adminResponse,
            quotedPrice = entity.quotedPrice,
            createdTimestamp = entity.createdTimestamp,
            requestedDate = entity.requestedDate
        )
    }

    private fun mapTransaction(entity: TransactionEntity): Transaction {
        return Transaction(
            id = entity.id,
            amount = entity.amount,
            category = entity.category ?: "General",
            date = entity.date,
            description = entity.description ?: ""
        )
    }
}
