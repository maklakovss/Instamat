package com.mss.imagesearcher.di

import android.content.Context
import androidx.room.Room
import com.mss.imagesearcher.model.repositories.DBRepository
import com.mss.imagesearcher.model.repositories.db.AppDatabase
import com.mss.imagesearcher.model.repositories.db.DBRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "history")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideDBRepository(appDatabase: AppDatabase): DBRepository {
        return DBRepositoryImpl(appDatabase)
    }
}