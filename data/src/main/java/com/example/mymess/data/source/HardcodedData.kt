package com.example.mymess.data.source

import com.example.mymess.domain.model.*
import java.util.UUID
import kotlin.random.Random

object HardcodedData {

    var messes = mutableListOf(
        Mess("mess-01", "Sunshine Mess", "owner-101", "123 College Road, Pune", "9876543210"),
        Mess("mess-02", "Elite Corporate Dining", "owner-102", "45 IT Park, Bangalore", "9123456780")
    )

    // --- ENRICHED USERS (15+ Entries) ---
    val users = mutableListOf(
        // Owner
        User("owner-101", "Vikram Singh", UserRole.OWNER, "Admin", "N/A", false, listOf("mess-01"), "owner@mymess.com", "9876543210"),
        
        // Demo Resident
        User("res-demo", "Demo Resident", UserRole.RESIDENT, "Software Engineer", "plan-002", false, listOf("mess-01"), "resident@mymess.com", "9999999999"),

        // Residents (mess-01)
        User("res-001", "Rahul Sharma", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "rahul@example.com", "9988776601"),
        User("res-002", "Amit Verma", UserRole.RESIDENT, "Employee", "plan-002", false, listOf("mess-01"), "amit@corp.com", "9988776602"),
        User("res-003", "Sneha Gupta", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "sneha@college.edu", "9988776603"),
        User("res-004", "Priya Patel", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "priya@example.com", "9988776604"),
        User("res-005", "Arjun Reddy", UserRole.RESIDENT, "Employee", "plan-002", true, listOf("mess-01"), "arjun@tech.com", "9988776605"), // Pending
        User("res-006", "Karan Johar", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "karan@example.com", "9988776606"),
        User("res-007", "Simran Kaur", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "simran@example.com", "9988776607"),
        User("res-008", "Rohan Mehta", UserRole.RESIDENT, "Employee", "plan-002", false, listOf("mess-01"), "rohan@bank.com", "9988776608"),
        User("res-009", "Vikaks Khanna", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "vikas@example.com", "9988776609"),
        User("res-010", "Anjali Devi", UserRole.RESIDENT, "Employee", "plan-002", false, listOf("mess-01"), "anjali@corp.com", "9988776610"),
        User("res-011", "Mohammed Ali", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "ali@example.com", "9988776611"),
        User("res-012", "John Doe", UserRole.RESIDENT, "Student", "plan-001", false, listOf("mess-01"), "john@example.com", "9988776612")
    )

    var mealSlots = mutableListOf(
        MealSlot("slot-01", "Breakfast", "07:30", "09:30"),
        MealSlot("slot-02", "Lunch", "12:30", "14:30"),
        MealSlot("slot-03", "Snacks", "17:00", "18:00"),
        MealSlot("slot-04", "Dinner", "19:30", "21:30")
    )

    val mealPlans = mutableListOf(
        MealPlan("plan-001", "Student Monthly", 3500.0, 60, 30, listOf("Student")),
        MealPlan("plan-002", "Employee Deluxe", 5000.0, 90, 30, listOf("Employee"))
    )

    // --- ENRICHED ATTENDANCE (Simulating past week) ---
    val attendanceHistory = generateMockAttendance()

    val payments = mutableListOf(
        Payment("pay-1", "res-001", "mess-01", 3500.0, "2024-12-01", "Student Monthly"),
        Payment("pay-2", "res-002", "mess-01", 5000.0, "2024-12-01", "Employee Deluxe"),
        Payment("pay-3", "res-004", "mess-01", 3500.0, "2024-12-02", "Student Monthly"),
        // Demo Data for 'res-demo'
        Payment("pay-demo-1", "res-demo", "mess-01", 3500.0, "2024-11-01", "Student Monthly (Nov)"),
        Payment("pay-demo-2", "res-demo", "mess-01", 150.0, "2024-11-15", "Guest Meal"),
        Payment("pay-demo-3", "res-demo", "mess-01", 3500.0, "2024-12-01", "Student Monthly (Dec)")
    )

    val leaves = mutableListOf<LeaveRequest>()

    // --- ENRICHED INVENTORY (20+ Items) ---
    var inventory = mutableListOf(
        InventoryItem("inv-01", "mess-01", "Basmati Rice", 45.0, "kg", 50.0, System.currentTimeMillis()),
        InventoryItem("inv-02", "mess-01", "Sona Masoori Rice", 100.0, "kg", 45.0, System.currentTimeMillis()),
        InventoryItem("inv-03", "mess-01", "Toor Dal", 25.0, "kg", 15.0, System.currentTimeMillis()),
        InventoryItem("inv-04", "mess-01", "Moong Dal", 10.0, "kg", 12.0, System.currentTimeMillis()),
        InventoryItem("inv-05", "mess-01", "Chana Dal", 8.0, "kg", 10.0, System.currentTimeMillis()),
        InventoryItem("inv-06", "mess-01", "Sunflower Oil", 30.0, "ltr", 15.0, System.currentTimeMillis()),
        InventoryItem("inv-07", "mess-01", "Mustard Oil", 5.0, "ltr", 5.0, System.currentTimeMillis()),
        InventoryItem("inv-08", "mess-01", "Wheat Flour (Atta)", 50.0, "kg", 30.0, System.currentTimeMillis()),
        InventoryItem("inv-09", "mess-01", "Maida", 5.0, "kg", 10.0, System.currentTimeMillis()),
        InventoryItem("inv-10", "mess-01", "Potatoes", 40.0, "kg", 20.0, System.currentTimeMillis()),
        InventoryItem("inv-11", "mess-01", "Onions", 35.0, "kg", 15.0, System.currentTimeMillis()),
        InventoryItem("inv-12", "mess-01", "Tomatoes", 15.0, "kg", 10.0, System.currentTimeMillis()),
        InventoryItem("inv-13", "mess-01", "Green Chilies", 2.0, "kg", 2.0, System.currentTimeMillis()),
        InventoryItem("inv-14", "mess-01", "Garlic", 1.0, "kg", 1.0, System.currentTimeMillis()),
        InventoryItem("inv-15", "mess-01", "Ginger", 1.0, "kg", 1.0, System.currentTimeMillis()),
        InventoryItem("inv-16", "mess-01", "Milk", 20.0, "ltr", 5.0, System.currentTimeMillis()),
        InventoryItem("inv-17", "mess-01", "Curd", 10.0, "kg", 5.0, System.currentTimeMillis()),
        InventoryItem("inv-18", "mess-01", "Paneer", 5.0, "kg", 5.0, System.currentTimeMillis()),
        InventoryItem("inv-19", "mess-01", "Salt", 10.0, "kg", 5.0, System.currentTimeMillis()),
        InventoryItem("inv-20", "mess-01", "Sugar", 15.0, "kg", 10.0, System.currentTimeMillis()),
        InventoryItem("inv-21", "mess-01", "Tea Powder", 2.0, "kg", 2.0, System.currentTimeMillis())
    )

    var weeklyMenu = mutableListOf(
        DailyMenu("mess-01", "Monday", "Idli Sambar, Chutney", "Rice, Dal Fry, Aloo Gobi, Curd", "Chapati, Mix Veg, Dal Tadka"),
        DailyMenu("mess-01", "Tuesday", "Poha, Sev, Tea", "Rice, Rajma Masala, Jeera Aloo", "Jeera Rice, Egg Curry / Paneer Butter Masala"),
        DailyMenu("mess-01", "Wednesday", "Masala Dosa, Sambar", "Curd Rice, Lemon Rice, Fryums", "Veg Biryani, Raita, Salan"),
        DailyMenu("mess-01", "Thursday", "Upma, Kesari Bath", "Rice, Leafy Dal, Cabbage Fry", "Chapati, Chicken Curry / Mushroom Masala"),
        DailyMenu("mess-01", "Friday", "Puri, Potato Bhaji", "Lemon Rice, Sambar, Papads", "Veg Fried Rice, Manchurian Gravy"),
        DailyMenu("mess-01", "Saturday", "Aloo Paratha, Curd", "Pulao, Kadhi Pakora", "Dal Khichdi, Pickle, Papadl"),
        DailyMenu("mess-01", "Sunday", "Mysore Bonda", "Special Thali (Sweet, Puri, Rice)", "Peas Pulao, Soyabean Curry")
    )

    var expenses = mutableListOf(
        Expense("exp-1", "mess-01", "Provisions (BigBasket)", 12500.0, System.currentTimeMillis() - 86400000 * 2, "Weekly Restock"),
        Expense("exp-2", "mess-01", "Vegetables (Local Market)", 3200.0, System.currentTimeMillis() - 86400000, "Daily Veggies"),
        Expense("exp-3", "mess-01", "Milk Bill (Nov)", 4500.0, System.currentTimeMillis() - 86400000 * 10, "Monthly"),
        Expense("exp-4", "mess-01", "Gas Cylinder Refill", 1100.0, System.currentTimeMillis() - 86400000 * 5, "LPG"),
        Expense("exp-5", "mess-01", "Cleaner Salary", 8000.0, System.currentTimeMillis() - 86400000 * 20, "Staff")
    )

    var complaints = mutableListOf(
        Complaint("tkt-1", "res-001", "mess-01", "Cold Food at Dinner", "The rice was cold yesterday.", "OPEN", System.currentTimeMillis()),
        Complaint("tkt-2", "res-002", "mess-01", "Cleanliness", "Dining tables were sticky.", "RESOLVED", System.currentTimeMillis() - 86400000),
        Complaint("tkt-3", "res-004", "mess-01", "Water Issue", "RO Water filter is leaking.", "IN_PROGRESS", System.currentTimeMillis() - 86400000 * 2),
        Complaint("tkt-4", "res-003", "mess-01", "Menu Request", "Please make Pav Bhaji on Sunday.", "OPEN", System.currentTimeMillis() - 86400000 * 3)
    )

    var wastage = mutableListOf(
        WastageEntry("w-1", "mess-01", System.currentTimeMillis() - 86400000, "Dinner", 2.5, "Extra Rice"),
        WastageEntry("w-2", "mess-01", System.currentTimeMillis() - 86400000 * 2, "Lunch", 1.2, "Curd Spoiled")
    )
    
    // --- SERVICE REQUESTS (Phase 31/32) ---
    var serviceRequests = mutableListOf(
        ServiceRequest(
            id = "sr-1", 
            residentId = "res-001", 
            messId = "mess-01", 
            type = "GUEST_MEAL", 
            title = "Sunday Lunch Guests",
            description = "2 Guests for Sunday Lunch", 
            status = "ACCEPTED", 
            adminResponse = "Approved",
            quotedPrice = 200.0,
            createdTimestamp = System.currentTimeMillis() - 86400000
        ),
        ServiceRequest(
            id = "sr-2", 
            residentId = "res-002", 
            messId = "mess-01", 
            type = "SPECIAL_DIET", 
            title = "Spicy Food Request",
            description = "Need mild spicy food for 2 days", 
            status = "PENDING", 
            createdTimestamp = System.currentTimeMillis() - 3600000
        ),
        ServiceRequest(
            id = "sr-3", 
            residentId = "res-demo", 
            messId = "mess-01", 
            type = "GUEST_MEAL", 
            title = "Dinner Guest",
            description = "Guest for Dinner tonight", 
            status = "PENDING", 
            createdTimestamp = System.currentTimeMillis()
        ),
        ServiceRequest(
            id = "sr-4", 
            residentId = "res-004", 
            messId = "mess-01", 
            type = "OTHER", 
            title = "ID Card Lost",
            description = "Lost my ID card, need replacement", 
            status = "COMPLETED", 
            adminResponse = "Issue new one tomorrow", 
            createdTimestamp = System.currentTimeMillis() - 86400000 * 5
        )
    )

    // --- UTILS ---
    private fun generateMockAttendance(): MutableList<AttendanceEntry> {
        val list = mutableListOf<AttendanceEntry>()
        val slots = listOf("Breakfast", "Lunch", "Dinner")
        val ids = listOf("res-001", "res-002", "res-003", "res-004")
        
        // Generate for last 3 days
        for (i in 0..2) {
            val timeBase = System.currentTimeMillis() - (i * 86400000)
            slots.forEach { slot ->
                ids.forEach { id ->
                     // Random attendance (80% chance)
                     if (Random.nextFloat() > 0.2) {
                         list.add(AttendanceEntry(
                             userId = id,
                             messId = "mess-01",
                             timestamp = timeBase, // Simplification
                             mealSlotName = slot,
                             isBilled = true
                         ))
                     }
                }
            }
        }
        return list
    }
}
