package com.example.mymess.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mymess.data.local.entities.*

@Database(
    entities = [
        UserEntity::class,
        ResidentEntity::class,
        TransactionEntity::class,
        AttendanceEntity::class,
        InventoryEntity::class,
        ServiceRequestEntity::class,
        WastageEntity::class
    ],
    version = 2, // Bump version
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MyMessDatabase : RoomDatabase() {
    abstract val messDao: MessDao
}
