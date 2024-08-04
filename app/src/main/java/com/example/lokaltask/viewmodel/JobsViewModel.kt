package com.example.lokaltask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lokaltask.data.local.SaveJobRepository
import com.example.lokaltask.model.JobResponse
import com.example.lokaltask.model.Result
import com.example.lokaltask.model.SavedJobResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel for managing job-related data and operations
class JobsViewModel(private val repository: SaveJobRepository) : ViewModel() {

    // LiveData objects to expose data from the repository to the UI
    val savedJobs: LiveData<List<SavedJobResult>> = repository.saveJobs
    val jobDetail: LiveData<Result> = repository.jobDetail
    val jobs: LiveData<JobResponse> = repository.jobs
    val jobAddStatus: LiveData<String> get() = _jobAddStatus
    private val _jobAddStatus = MutableLiveData<String>()

    // Fetch saved jobs from the repository and update LiveData
    fun getSavedJobs() {
        viewModelScope.launch {
            try {
                repository.getSavedJobs()
            } catch (e: Exception) {
                e.printStackTrace() // Log the exception
            }
        }
    }

    // Fetch jobs from the repository and update LiveData
    fun fetchJobs() {
        viewModelScope.launch {
            try {
                repository.getJobs()
            } catch (e: Exception) {
                e.printStackTrace() // Log the exception
            }
        }
    }

    // Fetch a specific job by its ID from the repository and update LiveData
    fun fetchJobById(jobId: Int) {
        viewModelScope.launch {
            try {
                repository.getJobById(jobId)
            } catch (e: Exception) {
                e.printStackTrace() // Log the exception
            }
        }
    }

    // Add a new job to the repository and update the jobAddStatus LiveData
    fun addJob(id: Int, title: String, location: String, salary: String, phone: String) {
        val newJob = SavedJobResult(id = id, title = title, location = location, salary = salary, phone = phone)
        viewModelScope.launch {
            val isJobAdded = repository.saveJob(newJob)
            if (isJobAdded) {
                _jobAddStatus.postValue("Job added successfully!") // Update status if job is added
            } else {
                _jobAddStatus.postValue("Job already exists!") // Update status if job already exists
            }
        }
    }

    // Delete a job from the repository by its ID
    fun deleteJob(jobId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteJob(jobId)
        }
    }
}
