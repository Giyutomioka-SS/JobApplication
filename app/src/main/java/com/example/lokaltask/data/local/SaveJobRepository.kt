package com.example.lokaltask.data.local

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lokaltask.data.network.ApiService
import com.example.lokaltask.model.JobResponse
import com.example.lokaltask.model.Result
import com.example.lokaltask.model.SavedJobResult
import com.example.lokaltask.data.network.NetworkUtils

class SaveJobRepository(
    private val apiService: ApiService,
    private val saveJobDatabase: SaveJobDatabase,
    private val applicationContext: Context,
) {
    // LiveData objects to hold job data
    private var jobLiveData = MutableLiveData<JobResponse>()
    private var jobDetailLiveData = MutableLiveData<Result>()

    // Public LiveData accessors
    val jobs: LiveData<JobResponse> get() = jobLiveData
    val jobDetail: LiveData<Result> get() = jobDetailLiveData

    // LiveData for saved jobs
    private val saveJobMutable = MutableLiveData<List<SavedJobResult>>()
    val saveJobs: LiveData<List<SavedJobResult>> get() = saveJobMutable

    // Function to fetch jobs from API
    suspend fun getJobs() {
        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {
                val result = apiService.getJobs(1)
                if (result.isSuccessful) {
                    Log.d("JobRepo", "API Response: ${result.body()}")
                    jobLiveData.postValue(result.body())
                } else {
                    Log.e("JobRepo", "API Error: ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("JobRepo", "Exception: ${e.message}")
            }
        } else {
            Log.e("JobRepo", "No internet connection")
        }
    }

    // Function to save a job to the local database
    suspend fun saveJob(job: SavedJobResult): Boolean {
        val existingJob = saveJobDatabase.jobsDao().getJobById(job.id)
        return if (existingJob == null) {
            saveJobDatabase.jobsDao().addJob(job)
            true // Job added successfully
        } else {
            Log.d("JobRepository", "Job with ID ${job.id} already exists.")
            false // Job already exists
        }
    }

    // Function to delete a job from the local database
    suspend fun deleteJob(jobId: Int) {
        saveJobDatabase.jobsDao().deleteJobById(jobId)
        getSavedJobs()
    }

    // Function to fetch saved jobs from the local database
    suspend fun getSavedJobs(){
        val jobs= saveJobDatabase.jobsDao().getJob()
        saveJobMutable.postValue(jobs)
    }

    // Function to fetch job details by ID from the API
    suspend fun getJobById(jobId: Int) {
        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            try {

                val result = apiService.getJobs(1)
                if (result.isSuccessful) {
                    val job = result.body()?.results?.find { it.id == jobId }
                    if (job != null) {
                        jobDetailLiveData.postValue(job!!)
                        Log.d("SaveCheck","here it is")
                    } else {
                        Log.e("JobRepo", "Job with ID $jobId not found")
                    }
                } else {
                    Log.e("JobRepo", "API Error: ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("JobRepo", "Exception: ${e.message}")
            }
        } else {
            Log.e("JobRepo", "No internet connection")
        }
    }


}