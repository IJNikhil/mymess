package com.example.mymess.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/mymess/data/local/MyMessDatabase;", "Landroidx/room/RoomDatabase;", "()V", "messDao", "Lcom/example/mymess/data/local/MessDao;", "getMessDao", "()Lcom/example/mymess/data/local/MessDao;", "data_debug"})
@androidx.room.Database(entities = {com.example.mymess.data.local.entities.UserEntity.class, com.example.mymess.data.local.entities.ResidentEntity.class, com.example.mymess.data.local.entities.TransactionEntity.class, com.example.mymess.data.local.entities.AttendanceEntity.class, com.example.mymess.data.local.entities.InventoryEntity.class, com.example.mymess.data.local.entities.ServiceRequestEntity.class, com.example.mymess.data.local.entities.WastageEntity.class}, version = 2, exportSchema = false)
@androidx.room.TypeConverters(value = {com.example.mymess.data.local.Converters.class})
public abstract class MyMessDatabase extends androidx.room.RoomDatabase {
    
    public MyMessDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.mymess.data.local.MessDao getMessDao();
}