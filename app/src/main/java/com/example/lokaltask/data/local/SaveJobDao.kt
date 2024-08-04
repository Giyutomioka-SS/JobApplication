package com.example.lokaltask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lokaltask.model.SavedJobResult

@Dao
// Data Access Object (DAO) for accessing the 'job' table in the local database
interface SaveJobDao {

    @Insert
    // Insert a new job into the 'job' table
    suspend fun addJob(job : SavedJobResult)

    // Retrieve all jobs from the 'job' table
    @Query("SELECT * FROM job")
    suspend fun getJob(): List<SavedJobResult>

    // Retrieve a specific job by its ID from the 'job' table
    @Query("SELECT * FROM job WHERE id = :jobId")
    suspend fun getJobById(jobId: Int): SavedJobResult?

    // Delete a specific job by its ID from the 'job' table
    @Query("DELETE FROM job WHERE id = :jobId")
    fun deleteJobById(jobId: Int)
}