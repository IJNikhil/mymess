package com.example.mymess.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00d4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u001d\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010\u0017\u001a\u00020\u0018H\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010\u001eJ$\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010 \u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J$\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u00112\u0006\u0010$\u001a\u00020%H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\'J\u001e\u0010(\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010)\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010*J\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020-0,H\u0096@\u00a2\u0006\u0002\u0010.J\u001c\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001d0,2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u001c\u00100\u001a\b\u0012\u0004\u0012\u0002010,2\u0006\u00102\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u001c\u00103\u001a\b\u0012\u0004\u0012\u0002040,2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u001c\u00105\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0011H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b6\u0010.J*\u00107\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u000209082\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010:\u001a\u00020;H\u0096@\u00a2\u0006\u0002\u0010<J&\u0010=\u001a\u00020>2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010?\u001a\u00020;2\u0006\u0010@\u001a\u00020;H\u0096@\u00a2\u0006\u0002\u0010AJ\u0014\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0,0CH\u0016J\u001c\u0010D\u001a\b\u0012\u0004\u0012\u00020E0,2\u0006\u00102\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u0018\u0010F\u001a\u0004\u0018\u00010-2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u001c\u0010G\u001a\b\u0012\u0004\u0012\u00020H0,2\u0006\u00102\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u001c\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0,0C2\u0006\u0010\u0007\u001a\u00020\bH\u0016J$\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00130\u00112\u0006\u0010K\u001a\u00020\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bL\u0010\"J\u0016\u0010M\u001a\u00020N2\u0006\u00102\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u0014\u0010O\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130,0CH\u0016J\u001c\u0010P\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0,0C2\u0006\u0010K\u001a\u00020\bH\u0016J\u001e\u0010Q\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0,0C2\b\u0010R\u001a\u0004\u0018\u00010\bH\u0016J\u0014\u0010S\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180,0CH\u0016J\u001c\u0010T\u001a\b\u0012\u0004\u0012\u00020U0,2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u001c\u0010V\u001a\b\u0012\u0004\u0012\u00020W0,2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u0016\u0010X\u001a\u00020\u00062\u0006\u0010Y\u001a\u00020UH\u0096@\u00a2\u0006\u0002\u0010ZJ\u0018\u0010[\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\\\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"J\u0010\u0010]\u001a\u00020%2\u0006\u0010^\u001a\u00020_H\u0002J\u0010\u0010`\u001a\u00020\u00182\u0006\u0010^\u001a\u00020aH\u0002J(\u0010b\u001a\u0004\u0018\u0001012\u0006\u0010c\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010d\u001a\u00020;H\u0096@\u00a2\u0006\u0002\u0010eJ\u0016\u0010f\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010\u001eJ\u000e\u0010g\u001a\u00020hH\u0082@\u00a2\u0006\u0002\u0010.J\u0016\u0010i\u001a\u00020\u00062\u0006\u0010j\u001a\u000204H\u0096@\u00a2\u0006\u0002\u0010kJ&\u0010l\u001a\u00020\u00062\u0006\u00102\u001a\u00020\b2\u0006\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010pJ\u0016\u0010q\u001a\u00020\u00062\u0006\u0010$\u001a\u00020EH\u0096@\u00a2\u0006\u0002\u0010rJ\u0016\u0010s\u001a\u00020\u00062\u0006\u0010t\u001a\u00020HH\u0096@\u00a2\u0006\u0002\u0010uJ.\u0010v\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010w\u001a\u00020\b2\u0006\u0010x\u001a\u00020\b2\u0006\u0010y\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010zJ$\u0010{\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b|\u0010}J\u001e\u0010~\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0096@\u00a2\u0006\u0002\u0010\u000fJ\'\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0007\u0010\u0080\u0001\u001a\u00020WH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u0081\u0001\u0010\u0082\u0001JC\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u00102\u001a\u00020\b2\u0007\u0010\u0084\u0001\u001a\u00020\b2\u0007\u0010\u0085\u0001\u001a\u00020\b2\t\u0010\u0086\u0001\u001a\u0004\u0018\u00010\bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0087\u0001\u0010zJ&\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020\u00060\u00112\u0006\u0010$\u001a\u00020%H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0089\u0001\u0010\'J\u0017\u0010\u008a\u0001\u001a\u00020\u00062\u0006\u00102\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\"R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u008b\u0001"}, d2 = {"Lcom/example/mymess/data/repository/RoomMessRepository;", "Lcom/example/mymess/domain/repository/MessRepository;", "dao", "Lcom/example/mymess/data/local/MessDao;", "(Lcom/example/mymess/data/local/MessDao;)V", "addExpense", "", "messId", "", "expense", "Lcom/example/mymess/domain/model/Expense;", "(Ljava/lang/String;Lcom/example/mymess/domain/model/Expense;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addInventoryItem", "item", "Lcom/example/mymess/domain/model/InventoryItem;", "(Ljava/lang/String;Lcom/example/mymess/domain/model/InventoryItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addResident", "Lkotlin/Result;", "resident", "Lcom/example/mymess/domain/model/Resident;", "addResident-gIAlu-s", "(Lcom/example/mymess/domain/model/Resident;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTransaction", "transaction", "Lcom/example/mymess/domain/model/Transaction;", "addTransaction-gIAlu-s", "(Lcom/example/mymess/domain/model/Transaction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addUser", "user", "Lcom/example/mymess/domain/model/User;", "(Lcom/example/mymess/domain/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "confirmServiceRequest", "requestId", "confirmServiceRequest-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createServiceRequest", "request", "Lcom/example/mymess/domain/model/ServiceRequest;", "createServiceRequest-gIAlu-s", "(Lcom/example/mymess/domain/model/ServiceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteInventoryItem", "itemId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMesses", "", "Lcom/example/mymess/domain/model/Mess;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllUsers", "getAttendanceHistory", "Lcom/example/mymess/domain/model/AttendanceEntry;", "userId", "getComplaints", "Lcom/example/mymess/domain/model/Complaint;", "getCurrentUser", "getCurrentUser-IoAF18A", "getDailyStats", "", "", "date", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFinancialStats", "Lcom/example/mymess/domain/model/FinancialReport;", "startDate", "endDate", "(Ljava/lang/String;JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getInventory", "Lkotlinx/coroutines/flow/Flow;", "getLeaveHistory", "Lcom/example/mymess/domain/model/LeaveRequest;", "getMessDetails", "getPaymentHistory", "Lcom/example/mymess/domain/model/Payment;", "getPendingVerifications", "getResidentById", "residentId", "getResidentById-gIAlu-s", "getResidentDashboardData", "Lcom/example/mymess/domain/model/ResidentDashboardData;", "getResidents", "getServiceRequests", "getServiceRequestsForOwner", "status", "getTransactions", "getWastage", "Lcom/example/mymess/domain/model/WastageEntry;", "getWeeklyMenu", "Lcom/example/mymess/domain/model/DailyMenu;", "logWastage", "entry", "(Lcom/example/mymess/domain/model/WastageEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "credentials", "mapServiceRequest", "entity", "Lcom/example/mymess/data/local/entities/ServiceRequestEntity;", "mapTransaction", "Lcom/example/mymess/data/local/entities/TransactionEntity;", "recordAttendance", "bioUserId", "timestamp", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerUser", "seedDatabase", "", "submitComplaint", "complaint", "(Lcom/example/mymess/domain/model/Complaint;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitFeedback", "rating", "", "comment", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitLeaveRequest", "(Lcom/example/mymess/domain/model/LeaveRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitPayment", "payment", "(Lcom/example/mymess/domain/model/Payment;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateDailyMenu", "dayOfWeek", "slot", "newContent", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateInventory", "updateInventory-gIAlu-s", "(Lcom/example/mymess/domain/model/InventoryItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateInventoryItem", "updateMenu", "menu", "updateMenu-gIAlu-s", "(Lcom/example/mymess/domain/model/DailyMenu;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateResidentProfile", "name", "phone", "idProofUri", "updateResidentProfile-yxL6bBk", "updateServiceRequest", "updateServiceRequest-gIAlu-s", "verifyUser", "data_debug"})
public final class RoomMessRepository implements com.example.mymess.domain.repository.MessRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.mymess.data.local.MessDao dao = null;
    
    @javax.inject.Inject()
    public RoomMessRepository(@org.jetbrains.annotations.NotNull()
    com.example.mymess.data.local.MessDao dao) {
        super();
    }
    
    private final java.lang.Object seedDatabase(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String credentials, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mymess.domain.model.User> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object registerUser(@org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAllMesses(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.Mess>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getMessDetails(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mymess.domain.model.Mess> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getResidentDashboardData(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mymess.domain.model.ResidentDashboardData> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAttendanceHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.AttendanceEntry>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getPaymentHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.Payment>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object submitFeedback(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, int rating, @org.jetbrains.annotations.NotNull()
    java.lang.String comment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getLeaveHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.LeaveRequest>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object submitLeaveRequest(@org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.LeaveRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getComplaints(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.Complaint>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object submitComplaint(@org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.Complaint complaint, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object recordAttendance(@org.jetbrains.annotations.NotNull()
    java.lang.String bioUserId, @org.jetbrains.annotations.NotNull()
    java.lang.String messId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mymess.domain.model.AttendanceEntry> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getWeeklyMenu(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.DailyMenu>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object verifyUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mymess.domain.model.User>> getPendingVerifications(@org.jetbrains.annotations.NotNull()
    java.lang.String messId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAllUsers(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.User>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addUser(@org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mymess.domain.model.Resident>> getResidents() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mymess.domain.model.Transaction>> getTransactions() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object submitPayment(@org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.Payment payment, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mymess.domain.model.InventoryItem>> getInventory() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mymess.domain.model.ServiceRequest>> getServiceRequests(@org.jetbrains.annotations.NotNull()
    java.lang.String residentId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mymess.domain.model.ServiceRequest>> getServiceRequestsForOwner(@org.jetbrains.annotations.Nullable()
    java.lang.String status) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getDailyStats(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, long date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.Map<java.lang.String, ? extends java.lang.Object>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getFinancialStats(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, long startDate, long endDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mymess.domain.model.FinancialReport> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getWastage(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.mymess.domain.model.WastageEntry>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object logWastage(@org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.WastageEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateDailyMenu(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    java.lang.String dayOfWeek, @org.jetbrains.annotations.NotNull()
    java.lang.String slot, @org.jetbrains.annotations.NotNull()
    java.lang.String newContent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addInventoryItem(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.InventoryItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateInventoryItem(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.InventoryItem item, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteInventoryItem(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object addExpense(@org.jetbrains.annotations.NotNull()
    java.lang.String messId, @org.jetbrains.annotations.NotNull()
    com.example.mymess.domain.model.Expense expense, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    private final com.example.mymess.domain.model.ServiceRequest mapServiceRequest(com.example.mymess.data.local.entities.ServiceRequestEntity entity) {
        return null;
    }
    
    private final com.example.mymess.domain.model.Transaction mapTransaction(com.example.mymess.data.local.entities.TransactionEntity entity) {
        return null;
    }
}