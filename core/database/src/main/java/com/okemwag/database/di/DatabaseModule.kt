package com.okemwag.database.di

import android.content.Context
import androidx.room.Room
import com.okemwag.database.PulseDatabase
import com.okemwag.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PulseDatabase {
        return Room.databaseBuilder(
            context,
            PulseDatabase::class.java,
            PulseDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: PulseDatabase): NewsDao {
        return database.newsDao()
    }

    @Provides
    @Singleton
    fun provideAlertDao(database: PulseDatabase): AlertDao {
        return database.alertDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: PulseDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideClassifiedDao(database: PulseDatabase): ClassifiedDao {
        return database.classifiedDao()
    }

    @Provides
    @Singleton
    fun provideDraftDao(database: PulseDatabase): DraftDao {
        return database.draftDao()
    }
}
