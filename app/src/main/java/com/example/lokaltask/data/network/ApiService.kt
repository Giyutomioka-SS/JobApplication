package com.example.lokaltask.data.network
import com.example.lokaltask.model.JobResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// Define an interface for the API service using Retrofit
interface ApiService {

    @GET("jobs")
    // Define a GET request to fetch jobs from the endpoint "jobs"
    suspend fun getJobs(@Query("page") page: Int): Response<JobResponse>
}