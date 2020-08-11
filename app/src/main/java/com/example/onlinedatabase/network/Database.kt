package com.example.onlinedatabase.network

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onlinedatabase.model.User

@androidx.room.Database(entities = [User::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract val databaseDao: DatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: Database? = null
        fun getInstance(context: Context): Database {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, Database::class.java, "database")
                        .fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }
}