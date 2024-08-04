package com.example.lokaltask

import android.app.Application
import com.example.lokaltask.data.network.ApiService
import com.example.lokaltask.data.network.RetrofitHelper
import com.example.lokaltask.data.local.SaveJobDatabase
import com.example.lokaltask.data.local.SaveJobRepository

// Custom Application class for initializing application-wide components
class LokalJobApplication : Application() {

    // A reference to the repository used for data operations
    lateinit var repository: SaveJobRepository

    override fun onCreate() {
        super.onCreate()
        // Initialize components when the application is created
        initialize()
    }

    private fun initialize() {

        // Create an instance of ApiService using RetrofitHelper
        val service = RetrofitHelper.getInstance().create(ApiService::class.java)

        // Get the instance of the Room database
        val database = SaveJobDatabase.getDatabase(applicationContext)

        // Initialize the repository with the ApiService and database instances
        repository = SaveJobRepository(service, database, applicationContext)
    }
}