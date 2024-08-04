package com.example.lokaltask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lokaltask.data.local.SaveJobRepository

// A factory class for creating instances of JobsViewModel
class JobsViewModelFactory(private val repository: SaveJobRepository) : ViewModelProvider.Factory {

    // Method to create an instance of the ViewModel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Check if the ViewModel class is JobsViewModel
        if (modelClass.isAssignableFrom(JobsViewModel::class.java)) {

            // If yes, create and return an instance of JobsViewModel with the repository
            return JobsViewModel(repository) as T
        }
        // If the ViewModel class is not JobsViewModel, throw an exception
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
