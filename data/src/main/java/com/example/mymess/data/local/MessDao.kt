package com.example.mymess.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mymess.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MessDao {

    // --- User Management ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE phoneNumber = :phone LIMIT 1")
    suspend fun getUserByPhone(phone: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE role = 'RESIDENT' AND isVerified = 0")
    fun getPendingVerifications(): Flow<List<UserEntity>>

    // --- Resident Management ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResident(resident: ResidentEntity)

    @Update
    suspend fun updateResident(resident: ResidentEntity)

    @Query("SELECT * FROM residents WHERE status = 'ACTIVE'")
    fun getActiveResidents(): Flow<List<ResidentEntity>>

    @Query("SELECT * FROM residents WHERE id = :residentId")
    suspend fun getResidentById(residentId: String): ResidentEntity?

    @Query("SELECT * FROM residents WHERE userId = :userId")
    suspend fun getResidentByUserId(userId: String): ResidentEntity?

    @Query("SELECT COUNT(*) FROM residents WHERE status = 'ACTIVE'")
    fun getActiveResidentCount(): Flow<Int>

    // --- Transactions & Finance ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE residentId = :residentId ORDER BY date DESC")
    fun getTransactionsForResident(residentId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE isExpense = 1 ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<TransactionEntity>>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = 'PAYMENT' AND date BETWEEN :start AND :end")
    fun getRevenueBetween(start: Long, end: Long): Flow<Double?> // Nullable if no rows

    @Query("SELECT SUM(amount) FROM transactions WHERE isExpense = 1 AND date BETWEEN :start AND :end")
    fun getExpensesBetween(start: Long, end: Long): Flow<Double?>

    // --- Attendance ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Query("SELECT * FROM attendance WHERE date = :date")
    fun getAttendanceForDate(date: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE residentId = :residentId AND date BETWEEN :start AND :end")
    fun getAttendanceForResident(residentId: String, start: Long, end: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT COUNT(*) FROM attendance WHERE date = :date AND status = 'PRESENT'")
    fun getPresentCount(date: Long): Flow<Int>

    // --- Inventory ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventoryItem(item: InventoryEntity)

    @Query("SELECT * FROM inventory_master")
    fun getAllInventory(): Flow<List<InventoryEntity>>

    @Update
    suspend fun updateInventory(item: InventoryEntity)

    // --- Service Requests ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServiceRequest(request: ServiceRequestEntity)

    @Update
    suspend fun updateServiceRequest(request: ServiceRequestEntity)

    @Query("SELECT * FROM service_requests WHERE residentId = :residentId ORDER BY createdTimestamp DESC")
    fun getRequestsForResident(residentId: String): Flow<List<ServiceRequestEntity>>

    @Query("SELECT * FROM service_requests WHERE status = :status")
    fun getRequestsByStatus(status: String): Flow<List<ServiceRequestEntity>>

    @Query("SELECT * FROM service_requests WHERE id = :requestId")
    suspend fun getRequestById(requestId: String): ServiceRequestEntity?
    
    // --- Generic Counts ---
    @Query("SELECT COUNT(*) FROM service_requests WHERE status = 'PENDING'")
    fun getPendingRequestCount(): Flow<Int>

    // --- Wastage ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWastage(wastage: WastageEntity)

    @Query("SELECT * FROM wastage WHERE messId = :messId ORDER BY date DESC")
    suspend fun getWastage(messId: String): List<WastageEntity>

    // --- Seeding Helpers ---
    @Query("SELECT COUNT(*) FROM inventory_master")
    suspend fun getInventoryCount(): Int

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getTransactionCount(): Int

    @Query("SELECT COUNT(*) FROM wastage")
    suspend fun getWastageCount(): Int

    @Query("SELECT COUNT(*) FROM service_requests")
    suspend fun getRequestCount(): Int
}
