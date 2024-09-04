package com.example.kbtech.Roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class], version = 2, exportSchema = false) // Updated version number
abstract class AppDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Define migration strategy from version 1 to version 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example migration logic: Adding a new column to a table
                database.execSQL("ALTER TABLE answer_table ADD COLUMN new_column_name TEXT")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "answer_database"
                )
                    .addMigrations(MIGRATION_1_2) // Include migration strategy
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

