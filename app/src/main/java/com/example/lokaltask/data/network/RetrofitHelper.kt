package com.example.lokaltask.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object to provide a Retrofit instance for making network requests.
object RetrofitHelper {

    // Base URL for the API endpoint
    private var BASE_URL="https://testapi.getlokalapp.com/common/"


    // Provides a Retrofit instance configured with the base URL and Gson converter.
    fun getInstance() : Retrofit{

        return Retrofit.Builder()
            // Set the base URL for the API requests
            .baseUrl(BASE_URL)
            // Add Gson converter to handle JSON serialization/deserialization
            .addConverterFactory(GsonConverterFactory.create())
            // Build and return the Retrofit instance
            .build()
    }

}