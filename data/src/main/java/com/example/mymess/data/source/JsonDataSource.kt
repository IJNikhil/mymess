package com.example.mymess.data.source

import android.content.Context
import com.example.mymess.domain.model.*
import com.example.mymess.data.source.HardcodedData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

// Data Model to wrap everything in one JSON file
data class MyMessDatabase(
    val messes: MutableList<Mess>,
    val users: MutableList<User>,
    val mealSlots: MutableList<MealSlot>,
    val mealPlans: MutableList<MealPlan>,
    val attendanceHistory: MutableList<AttendanceEntry>,
    val payments: MutableList<Payment>,
    val leaves: MutableList<LeaveRequest>,
    val inventory: MutableList<InventoryItem>,
    val weeklyMenu: MutableList<DailyMenu>,
    val expenses: MutableList<Expense>,
    val complaints: MutableList<Complaint>,
    val wastage: MutableList<WastageEntry>
)

@Singleton
class JsonDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val gson = Gson()
    private val fileName = "mymess_db_v3.json"
    private val file by lazy { File(context.filesDir, fileName) }
    
    // In-Memory Cache (initialized from file or Hardcoded)
    var db: MyMessDatabase
        private set

    init {
        db = loadDatabase()
    }

    private fun loadDatabase(): MyMessDatabase {
        if (!file.exists()) {
            return MyMessDatabase(
                messes = HardcodedData.messes,
                users = HardcodedData.users,
                mealSlots = HardcodedData.mealSlots,
                mealPlans = HardcodedData.mealPlans,
                attendanceHistory = HardcodedData.attendanceHistory,
                payments = HardcodedData.payments,
                leaves = HardcodedData.leaves,
                inventory = HardcodedData.inventory,
                weeklyMenu = HardcodedData.weeklyMenu,
                expenses = HardcodedData.expenses,
                complaints = HardcodedData.complaints,
                wastage = HardcodedData.wastage
            ).also { saveData(it) }
        }

        return try {
            val json = file.readText()
            val loadedDb = gson.fromJson(json, MyMessDatabase::class.java)
            
            // --- MIGRATION: Ensure Demo Credentials Exist ---
            var dataChanged = false
            
            // 1. Update Owner Email
            val owner = loadedDb.users.find { it.id == "owner-101" }
            if (owner != null && owner.email != "owner@mymess.com") {
                val index = loadedDb.users.indexOf(owner)
                loadedDb.users[index] = owner.copy(email = "owner@mymess.com")
                dataChanged = true
            }
            
            // 2. Ensure Demo Resident Exists
            if (loadedDb.users.none { it.id == "res-demo" }) {
                loadedDb.users.add(
                    User("res-demo", "Demo Resident", UserRole.RESIDENT, "Software Engineer", "plan-002", false, listOf("mess-01"), "resident@mymess.com", "9999999999")
                )
                dataChanged = true
            }
            
            // 3. Ensure "resident@mymess.com" matches "res-demo" (in case user modified it?)
            // Just ensure there is SOME user with resident@mymess.com
            
            if (dataChanged) {
                 saveData(loadedDb)
            }
            
            loadedDb
        } catch (e: Exception) {
            e.printStackTrace()
             MyMessDatabase(
                messes = HardcodedData.messes,
                users = HardcodedData.users,
                mealSlots = HardcodedData.mealSlots,
                mealPlans = HardcodedData.mealPlans,
                attendanceHistory = HardcodedData.attendanceHistory,
                payments = HardcodedData.payments,
                leaves = HardcodedData.leaves,
                inventory = HardcodedData.inventory,
                weeklyMenu = HardcodedData.weeklyMenu,
                expenses = HardcodedData.expenses,
                complaints = HardcodedData.complaints,
                wastage = HardcodedData.wastage
            )
        }
    }

    fun saveData() {
        // Save current in-memory db to file
        saveData(db)
    }

    private fun saveData(database: MyMessDatabase) {
        try {
            val json = gson.toJson(database)
            file.writeText(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    // Helper to refresh from disk (optional)
    fun reload() {
        db = loadDatabase()
    }
}
