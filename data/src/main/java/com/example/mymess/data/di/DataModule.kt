package com.example.mymess.data.di

import com.example.mymess.data.repository.RoomMessRepository
import com.example.mymess.data.source.UserPreferences
import com.example.mymess.domain.repository.MessRepository
import com.example.mymess.domain.repository.SessionStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMessRepository(
        roomMessRepository: RoomMessRepository
    ): MessRepository

    companion object {
        @dagger.Provides
        @Singleton
        fun provideDatabase(
            @dagger.hilt.android.qualifiers.ApplicationContext context: android.content.Context
        ): com.example.mymess.data.local.MyMessDatabase {
            return androidx.room.Room.databaseBuilder(
                context,
                com.example.mymess.data.local.MyMessDatabase::class.java,
                "mymess_database"
            )
            .fallbackToDestructiveMigration() // For MVP dev speed
            .build()
        }

        @dagger.Provides
        @Singleton
        fun provideMessDao(database: com.example.mymess.data.local.MyMessDatabase): com.example.mymess.data.local.MessDao {
            return database.messDao
        }
    }

    @Binds
    @Singleton
    abstract fun bindSessionStorage(
        userPreferences: UserPreferences
    ): SessionStorage

}
