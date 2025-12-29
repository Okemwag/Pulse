package com.okemwag.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okemwag.database.dao.*
import com.okemwag.database.entity.*

/**
 * Pulse Room Database
 * 
 * Local database for offline-first data persistence.
 */
@Database(
    entities = [
        NewsEntity::class,
        AlertEntity::class,
        UserEntity::class,
        ClassifiedEntity::class,
        DraftEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class PulseDatabase : RoomDatabase() {
    
    abstract fun newsDao(): NewsDao
    abstract fun alertDao(): AlertDao
    abstract fun userDao(): UserDao
    abstract fun classifiedDao(): ClassifiedDao
    abstract fun draftDao(): DraftDao
    
    companion object {
        const val DATABASE_NAME = "pulse_database"
    }
}
