package com.example.lokaltask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lokaltask.activity.JobDetails
import com.example.lokaltask.databinding.JobItemviewBinding
import com.example.lokaltask.model.Result

// Adapter for displaying a list of jobs in a RecyclerView
class JobsAdapter(private var jobs: List<Result>, private val context: Context) :
    RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    // Initialize the jobs list by filtering out items with null or empty fields
    init {
        jobs = jobs.filter { job ->
            !job.title.isNullOrEmpty() &&
                    !job.primary_details?.Place.isNullOrEmpty() &&
                    !job.primary_details?.Salary.isNullOrEmpty() &&
                    !job.whatsapp_no.isNullOrEmpty()
        }
    }

    // ViewHolder class for holding the view binding of a job item
    class JobViewHolder(val binding: JobItemviewBinding) : RecyclerView.ViewHolder(binding.root)

    // Inflate the job item view and create a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = JobItemviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    // Bind the data to the view elements in the ViewHolder
    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        with(holder.binding) {

            // Set the job title and its visibility
            jobTitle.text = job.title
            jobTitle.visibility = if (job.title.isNullOrEmpty()) View.GONE else View.VISIBLE


            // Set the job location and its visibility
            if (!job.primary_details?.Place.isNullOrEmpty()) {
                location.text = job.primary_details?.Place
                location.visibility = View.VISIBLE
            } else {
                location.visibility = View.GONE
            }

            // Set the job salary and its visibility
            if (!job.primary_details?.Salary.isNullOrEmpty()) {
                salary.text = job.primary_details?.Salary
                salary.visibility = View.VISIBLE
            } else {
                salary.visibility = View.GONE
            }

            // Set the job WhatsApp number and its visibility
            if (!job.whatsapp_no.isNullOrEmpty()) {
                phone.text = job.whatsapp_no
                phone.visibility = View.VISIBLE
            } else {
                phone.visibility = View.GONE
            }
        }

        // Set an OnClickListener to navigate to the job details activity with job id and status
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, JobDetails::class.java).putExtra("id", job.id).putExtra("status","1"))
        }
    }

    // Return the size of the jobs list
    override fun getItemCount() = jobs.size

    // Update the jobs list and notify the adapter
    fun updateJobs(newJobs: List<Result>) {
        jobs = newJobs.filter { job ->
            !job.title.isNullOrEmpty() &&
                    !job.primary_details?.Place.isNullOrEmpty() &&
                    !job.primary_details?.Salary.isNullOrEmpty() &&
                    !job.whatsapp_no.isNullOrEmpty()
        }
        notifyDataSetChanged()
    }
}
