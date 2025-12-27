package com.example.mymess.data.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'\u00a8\u0006\f"}, d2 = {"Lcom/example/mymess/data/di/DataModule;", "", "()V", "bindMessRepository", "Lcom/example/mymess/domain/repository/MessRepository;", "roomMessRepository", "Lcom/example/mymess/data/repository/RoomMessRepository;", "bindSessionStorage", "Lcom/example/mymess/domain/repository/SessionStorage;", "userPreferences", "Lcom/example/mymess/data/source/UserPreferences;", "Companion", "data_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class DataModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.mymess.data.di.DataModule.Companion Companion = null;
    
    public DataModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.mymess.domain.repository.MessRepository bindMessRepository(@org.jetbrains.annotations.NotNull()
    com.example.mymess.data.repository.RoomMessRepository roomMessRepository);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.mymess.domain.repository.SessionStorage bindSessionStorage(@org.jetbrains.annotations.NotNull()
    com.example.mymess.data.source.UserPreferences userPreferences);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\n"}, d2 = {"Lcom/example/mymess/data/di/DataModule$Companion;", "", "()V", "provideDatabase", "Lcom/example/mymess/data/local/MyMessDatabase;", "context", "Landroid/content/Context;", "provideMessDao", "Lcom/example/mymess/data/local/MessDao;", "database", "data_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @dagger.Provides()
        @javax.inject.Singleton()
        @org.jetbrains.annotations.NotNull()
        public final com.example.mymess.data.local.MyMessDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
        @org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        @dagger.Provides()
        @javax.inject.Singleton()
        @org.jetbrains.annotations.NotNull()
        public final com.example.mymess.data.local.MessDao provideMessDao(@org.jetbrains.annotations.NotNull()
        com.example.mymess.data.local.MyMessDatabase database) {
            return null;
        }
    }
}