package com.example.mymess.domain.model

data class Mess(
    val id: String,
    val name: String,
    val ownerId: String,
    val address: String,
    val contact: String
)

data class User(
    val id: String,
    val name: String,
    val role: UserRole, // "RESIDENT", "OWNER", "ADMIN"
    val designation: String = "Resident", // e.g., "Student", "Staff"
    val currentPlanId: String = "plan-basic",
    // New Fields for V3
    val pendingVerification: Boolean = false,
    val linkedMesses: List<String> = emptyList(), // IDs of messes they are joined to
    val email: String? = null,
    val phone: String? = null
)

enum class UserRole {
    RESIDENT, OWNER, ADMIN
}

data class Resident(
    val id: String,
    val userId: String,
    val name: String,
    val roomNumber: String,
    val phoneNumber: String,
    val plan: MealPlan,
    val outstandingDues: Double,
    val profilePictureUrl: String?,
    val idProofUrl: String? = null
)

data class MealSlot(
    val id: String,
    val name: String, // Breakfast, Lunch, etc.
    val startTime: String,
    val endTime: String
)

data class MealPlan(
    val id: String,
    val name: String,
    val price: Double,
    val mealsPerMonth: Int,
    val validityDays: Int,
    val allowedDesignations: List<String>
)

data class AttendanceEntry(
    val userId: String,
    val messId: String, // New: Link to specific mess
    val timestamp: Long,
    val mealSlotName: String,
    val isBilled: Boolean,
    val adjustmentReason: String? = null
)

data class ResidentDashboardData(
    val remainingQuota: Int,
    val refreshDate: String,
    val runningBill: Double,
    val takenMealsToday: List<String>
)

data class InventoryItem(
    val id: String,
    val messId: String, // New
    val name: String,
    val quantity: Double,
    val unit: String, // kg, ltr, etc.
    val minThreshold: Double,
    val lastUpdated: Long
)

data class DailyMenu(
    val messId: String, // New
    val dayOfWeek: String, // "Monday", "Tuesday"...
    val breakfast: String,
    val lunch: String,
    val dinner: String,
    val special: String? = null
)

data class Payment(
    val id: String,
    val userId: String,
    val messId: String, // New
    val amount: Double,
    val date: String,
    val description: String
)

data class LeaveRequest(
    val id: String,
    val userId: String,
    val messId: String, // New
    val startDate: Long,
    val endDate: Long,
    val reason: String,
    val status: String // "PENDING", "APPROVED", "REJECTED"
)

data class Complaint(
    val id: String,
    val userId: String,
    val messId: String, // New
    val title: String,
    val description: String,
    val status: String, // "OPEN", "RESOLVED"
    val filedDate: Long
)

data class Expense(
    val id: String,
    val messId: String, // New
    val category: String,
    val amount: Double,
    val date: Long,
    val description: String
)

data class WastageEntry(
    val id: String,
    val messId: String,
    val date: Long,
    val mealType: String, // Breakfast, Lunch, Dinner
    val quantityKg: Double,
    val reason: String = "Leftovers"
)

data class FinancialReport(
    val id: String,
    val messId: String,
    val month: String,
    val revenue: Double,
    val expenses: Double,
    val profit: Double,
    val generatedDate: Long
)

data class ServiceRequest(
    val id: String,
    val residentId: String,
    val messId: String,
    val type: String, // GUEST_MEAL, COMPLAINT, LEAVE
    val title: String, // Short summary
    val description: String, // Full details
    val status: String, // PENDING, ACCEPTED, REJECTED, COMPLETED
    val adminResponse: String? = null,
    val quotedPrice: Double? = null,
    val createdTimestamp: Long = System.currentTimeMillis(),
    val requestedDate: Long = System.currentTimeMillis()
)

data class Transaction(
    val id: String,
    val amount: Double,
    val category: String,
    val date: Long,
    val description: String
)
