package com.example.lokaltask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lokaltask.model.SavedJobResult

// Define the Room database with the SavedJobResult entity and version 1
@Database(entities = [SavedJobResult::class], version = 1)
abstract class SaveJobDatabase : RoomDatabase() {

    // Abstract method to get the DAO (Data Access Object) for SavedJobResult
    abstract fun jobsDao(): SaveJobDao

    companion object {
        // Singleton instance of the database
        private var INSTANCE: SaveJobDatabase? = null

        // Method to get the database instance
        fun getDatabase(context: Context): SaveJobDatabase {
            // Ensure only one thread can access this block at a time
            synchronized(this) {
                // If the INSTANCE is null, create a new database instance
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SaveJobDatabase::class.java,
                        "jobDB"
                    ).build()
                }
            }
            // Return the database instance
            return INSTANCE!!
        }
    }
}