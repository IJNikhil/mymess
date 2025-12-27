package com.example.mymess.data.source;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u0006H\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0006H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/mymess/data/source/JsonDataSource;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "<set-?>", "Lcom/example/mymess/data/source/MyMessDatabase;", "db", "getDb", "()Lcom/example/mymess/data/source/MyMessDatabase;", "file", "Ljava/io/File;", "getFile", "()Ljava/io/File;", "file$delegate", "Lkotlin/Lazy;", "fileName", "", "gson", "Lcom/google/gson/Gson;", "loadDatabase", "reload", "", "saveData", "database", "data_debug"})
public final class JsonDataSource {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fileName = "mymess_db_v3.json";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy file$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private com.example.mymess.data.source.MyMessDatabase db;
    
    @javax.inject.Inject()
    public JsonDataSource(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final java.io.File getFile() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.mymess.data.source.MyMessDatabase getDb() {
        return null;
    }
    
    private final com.example.mymess.data.source.MyMessDatabase loadDatabase() {
        return null;
    }
    
    public final void saveData() {
    }
    
    private final void saveData(com.example.mymess.data.source.MyMessDatabase database) {
    }
    
    public final void reload() {
    }
}