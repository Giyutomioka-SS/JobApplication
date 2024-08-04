package com.example.lokaltask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lokaltask.activity.JobDetails
import com.example.lokaltask.databinding.JobItemviewBinding
import com.example.lokaltask.model.SavedJobResult

// Adapter class for managing a list of jobs in a RecyclerView
class JobsAdapter2(private var jobs: List<SavedJobResult>, private val context: Context) :
    RecyclerView.Adapter<JobsAdapter2.JobViewHolder>() {

    // ViewHolder class that holds the view for each job item
    class JobViewHolder(val binding: JobItemviewBinding) : RecyclerView.ViewHolder(binding.root)

    // Inflates the item view and returns a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = JobItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    // Binds data to the item view for the given position
    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        with(holder.binding) {

            jobTitle.text = job.title
            location.text = job.location
            salary.text = job.salary
            phone.text = job.phone

        }

        // Sets a click listener to start the JobDetails activity with job details
        holder.itemView.setOnClickListener{
            context.startActivity(Intent(context, JobDetails::class.java).putExtra("id",job.id).putExtra("status","2"))
        }
    }

    // Returns the total number of job items
    override fun getItemCount() = jobs.size

    // Updates the list of jobs and notifies the adapter to refresh the view
    fun updateJobs(newJobs: List<SavedJobResult>) {
        jobs = newJobs
        notifyDataSetChanged()
    }
}
