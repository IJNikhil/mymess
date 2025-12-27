package com.example.mymess.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MyMessDatabase_Impl extends MyMessDatabase {
  private volatile MessDao _messDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `role` TEXT NOT NULL, `isVerified` INTEGER NOT NULL, `profilePhotoUrl` TEXT, `phoneNumber` TEXT NOT NULL, `email` TEXT, `passwordHash` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `residents` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `roomNumber` TEXT NOT NULL, `planId` TEXT NOT NULL, `status` TEXT NOT NULL, `balance` REAL NOT NULL, `idProofUrl` TEXT, `joinDate` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`userId`) REFERENCES `users`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_residents_userId` ON `residents` (`userId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `transactions` (`id` TEXT NOT NULL, `residentId` TEXT, `type` TEXT NOT NULL, `category` TEXT, `amount` REAL NOT NULL, `date` INTEGER NOT NULL, `description` TEXT, `paymentMode` TEXT, `isExpense` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_residentId_date` ON `transactions` (`residentId`, `date`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_transactions_category_date` ON `transactions` (`category`, `date`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `attendance` (`id` TEXT NOT NULL, `residentId` TEXT NOT NULL, `date` INTEGER NOT NULL, `mealType` TEXT NOT NULL, `status` TEXT NOT NULL, `isBilled` INTEGER NOT NULL, `markedAt` INTEGER, `remarks` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_attendance_residentId_date_mealType` ON `attendance` (`residentId`, `date`, `mealType`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `inventory_master` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `category` TEXT NOT NULL, `unit` TEXT NOT NULL, `currentQuantity` REAL NOT NULL, `minThreshold` REAL NOT NULL, `unitPrice` REAL, `isActive` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `service_requests` (`id` TEXT NOT NULL, `residentId` TEXT NOT NULL, `type` TEXT NOT NULL, `status` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `requestedDate` INTEGER NOT NULL, `quotedPrice` REAL, `adminResponse` TEXT, `createdTimestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `wastage` (`id` TEXT NOT NULL, `messId` TEXT NOT NULL, `date` INTEGER NOT NULL, `mealType` TEXT NOT NULL, `quantityKg` REAL NOT NULL, `reason` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '51c68082be57fce9b5e4cad5a038556f')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `residents`");
        db.execSQL("DROP TABLE IF EXISTS `transactions`");
        db.execSQL("DROP TABLE IF EXISTS `attendance`");
        db.execSQL("DROP TABLE IF EXISTS `inventory_master`");
        db.execSQL("DROP TABLE IF EXISTS `service_requests`");
        db.execSQL("DROP TABLE IF EXISTS `wastage`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(8);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isVerified", new TableInfo.Column("isVerified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profilePhotoUrl", new TableInfo.Column("profilePhotoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("passwordHash", new TableInfo.Column("passwordHash", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.example.mymess.data.local.entities.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsResidents = new HashMap<String, TableInfo.Column>(8);
        _columnsResidents.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResidents.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResidents.put("roomNumber", new TableInfo.Column("roomNumber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResidents.put("planId", new TableInfo.Column("planId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResidents.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResidents.put("balance", new TableInfo.Column("balance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResidents.put("idProofUrl", new TableInfo.Column("idProofUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsResidents.put("joinDate", new TableInfo.Column("joinDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysResidents = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysResidents.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesResidents = new HashSet<TableInfo.Index>(1);
        _indicesResidents.add(new TableInfo.Index("index_residents_userId", true, Arrays.asList("userId"), Arrays.asList("ASC")));
        final TableInfo _infoResidents = new TableInfo("residents", _columnsResidents, _foreignKeysResidents, _indicesResidents);
        final TableInfo _existingResidents = TableInfo.read(db, "residents");
        if (!_infoResidents.equals(_existingResidents)) {
          return new RoomOpenHelper.ValidationResult(false, "residents(com.example.mymess.data.local.entities.ResidentEntity).\n"
                  + " Expected:\n" + _infoResidents + "\n"
                  + " Found:\n" + _existingResidents);
        }
        final HashMap<String, TableInfo.Column> _columnsTransactions = new HashMap<String, TableInfo.Column>(9);
        _columnsTransactions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("residentId", new TableInfo.Column("residentId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("category", new TableInfo.Column("category", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("paymentMode", new TableInfo.Column("paymentMode", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTransactions.put("isExpense", new TableInfo.Column("isExpense", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTransactions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTransactions = new HashSet<TableInfo.Index>(2);
        _indicesTransactions.add(new TableInfo.Index("index_transactions_residentId_date", false, Arrays.asList("residentId", "date"), Arrays.asList("ASC", "ASC")));
        _indicesTransactions.add(new TableInfo.Index("index_transactions_category_date", false, Arrays.asList("category", "date"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoTransactions = new TableInfo("transactions", _columnsTransactions, _foreignKeysTransactions, _indicesTransactions);
        final TableInfo _existingTransactions = TableInfo.read(db, "transactions");
        if (!_infoTransactions.equals(_existingTransactions)) {
          return new RoomOpenHelper.ValidationResult(false, "transactions(com.example.mymess.data.local.entities.TransactionEntity).\n"
                  + " Expected:\n" + _infoTransactions + "\n"
                  + " Found:\n" + _existingTransactions);
        }
        final HashMap<String, TableInfo.Column> _columnsAttendance = new HashMap<String, TableInfo.Column>(8);
        _columnsAttendance.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("residentId", new TableInfo.Column("residentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("mealType", new TableInfo.Column("mealType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("isBilled", new TableInfo.Column("isBilled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("markedAt", new TableInfo.Column("markedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAttendance.put("remarks", new TableInfo.Column("remarks", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAttendance = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAttendance = new HashSet<TableInfo.Index>(1);
        _indicesAttendance.add(new TableInfo.Index("index_attendance_residentId_date_mealType", true, Arrays.asList("residentId", "date", "mealType"), Arrays.asList("ASC", "ASC", "ASC")));
        final TableInfo _infoAttendance = new TableInfo("attendance", _columnsAttendance, _foreignKeysAttendance, _indicesAttendance);
        final TableInfo _existingAttendance = TableInfo.read(db, "attendance");
        if (!_infoAttendance.equals(_existingAttendance)) {
          return new RoomOpenHelper.ValidationResult(false, "attendance(com.example.mymess.data.local.entities.AttendanceEntity).\n"
                  + " Expected:\n" + _infoAttendance + "\n"
                  + " Found:\n" + _existingAttendance);
        }
        final HashMap<String, TableInfo.Column> _columnsInventoryMaster = new HashMap<String, TableInfo.Column>(8);
        _columnsInventoryMaster.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventoryMaster.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventoryMaster.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventoryMaster.put("unit", new TableInfo.Column("unit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventoryMaster.put("currentQuantity", new TableInfo.Column("currentQuantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventoryMaster.put("minThreshold", new TableInfo.Column("minThreshold", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventoryMaster.put("unitPrice", new TableInfo.Column("unitPrice", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInventoryMaster.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInventoryMaster = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesInventoryMaster = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoInventoryMaster = new TableInfo("inventory_master", _columnsInventoryMaster, _foreignKeysInventoryMaster, _indicesInventoryMaster);
        final TableInfo _existingInventoryMaster = TableInfo.read(db, "inventory_master");
        if (!_infoInventoryMaster.equals(_existingInventoryMaster)) {
          return new RoomOpenHelper.ValidationResult(false, "inventory_master(com.example.mymess.data.local.entities.InventoryEntity).\n"
                  + " Expected:\n" + _infoInventoryMaster + "\n"
                  + " Found:\n" + _existingInventoryMaster);
        }
        final HashMap<String, TableInfo.Column> _columnsServiceRequests = new HashMap<String, TableInfo.Column>(10);
        _columnsServiceRequests.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("residentId", new TableInfo.Column("residentId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("requestedDate", new TableInfo.Column("requestedDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("quotedPrice", new TableInfo.Column("quotedPrice", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("adminResponse", new TableInfo.Column("adminResponse", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsServiceRequests.put("createdTimestamp", new TableInfo.Column("createdTimestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysServiceRequests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesServiceRequests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoServiceRequests = new TableInfo("service_requests", _columnsServiceRequests, _foreignKeysServiceRequests, _indicesServiceRequests);
        final TableInfo _existingServiceRequests = TableInfo.read(db, "service_requests");
        if (!_infoServiceRequests.equals(_existingServiceRequests)) {
          return new RoomOpenHelper.ValidationResult(false, "service_requests(com.example.mymess.data.local.entities.ServiceRequestEntity).\n"
                  + " Expected:\n" + _infoServiceRequests + "\n"
                  + " Found:\n" + _existingServiceRequests);
        }
        final HashMap<String, TableInfo.Column> _columnsWastage = new HashMap<String, TableInfo.Column>(6);
        _columnsWastage.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWastage.put("messId", new TableInfo.Column("messId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWastage.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWastage.put("mealType", new TableInfo.Column("mealType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWastage.put("quantityKg", new TableInfo.Column("quantityKg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWastage.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWastage = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWastage = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWastage = new TableInfo("wastage", _columnsWastage, _foreignKeysWastage, _indicesWastage);
        final TableInfo _existingWastage = TableInfo.read(db, "wastage");
        if (!_infoWastage.equals(_existingWastage)) {
          return new RoomOpenHelper.ValidationResult(false, "wastage(com.example.mymess.data.local.entities.WastageEntity).\n"
                  + " Expected:\n" + _infoWastage + "\n"
                  + " Found:\n" + _existingWastage);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "51c68082be57fce9b5e4cad5a038556f", "fbbdc5359b8b4900e303ab10350e52df");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","residents","transactions","attendance","inventory_master","service_requests","wastage");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `residents`");
      _db.execSQL("DELETE FROM `transactions`");
      _db.execSQL("DELETE FROM `attendance`");
      _db.execSQL("DELETE FROM `inventory_master`");
      _db.execSQL("DELETE FROM `service_requests`");
      _db.execSQL("DELETE FROM `wastage`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MessDao.class, MessDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MessDao getMessDao() {
    if (_messDao != null) {
      return _messDao;
    } else {
      synchronized(this) {
        if(_messDao == null) {
          _messDao = new MessDao_Impl(this);
        }
        return _messDao;
      }
    }
  }
}
