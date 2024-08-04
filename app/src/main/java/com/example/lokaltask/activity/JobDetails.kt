package com.example.lokaltask.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lokaltask.LokalJobApplication
import com.example.lokaltask.databinding.ActivityJobDetailsBinding
import com.example.lokaltask.data.local.SaveJobRepository
import com.example.lokaltask.viewmodel.JobsViewModel
import com.example.lokaltask.viewmodel.JobsViewModelFactory
import kotlin.properties.Delegates

class JobDetails : AppCompatActivity() {

    private lateinit var binding: ActivityJobDetailsBinding
    private lateinit var viewModel: JobsViewModel
    private var jobId: Int? = null
    private lateinit var repository: SaveJobRepository
    private var phone="Not Available"
    private lateinit var status: String
    private var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this activity
        binding = ActivityJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get job ID and status from the intent extras
        jobId = intent.getIntExtra("id", -1)
        status= intent.getStringExtra("status").toString()

        // Set visibility of action buttons based on job status
        if(status=="1"){
            binding.floatingActionButtonAddJob.visibility = View.VISIBLE
            binding.floatingActionButtonRemoveJob.visibility = View.GONE
        }
        else{
            binding.floatingActionButtonRemoveJob.visibility = View.VISIBLE
            binding.floatingActionButtonAddJob.visibility = View.GONE
        }

        // If no valid job ID, return early
        if (jobId == -1) {
            return
        }

        // Initialize the repository and ViewModel
        val application = application as LokalJobApplication
        repository = application.repository
        val factory = JobsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[JobsViewModel::class.java]

        // Observe job addition status and show toast messages
        viewModel.jobAddStatus.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        // Observe job details and update UI accordingly
        viewModel.jobDetail.observe(this) { jobDetail ->

            if (jobDetail != null) {
                // Update UI with job details
                binding.textViewTitle.text = jobDetail.title
                binding.textViewCompanyName.text = jobDetail.company_name
                binding.textViewJobType.text = jobDetail.primary_details.Job_Type
                binding.textViewSalary.text = jobDetail.primary_details.Salary
                binding.textViewExperience.text = jobDetail.primary_details.Experience
                binding.textViewQualification.text = jobDetail.primary_details.Qualification
                binding.textViewLocation.text = jobDetail.primary_details.Place

                // Store phone number and job ID
                phone= jobDetail.whatsapp_no
                id=jobDetail.id

                // Load job image using Glide
                Glide.with(this)
                    .load(jobDetail.creatives.firstOrNull()?.file)
                    .into(binding.imageViewJob)

                // Set up call button to open dialer with job's custom link
                binding.buttonCall.setOnClickListener {
                    val callIntent = Intent(Intent.ACTION_DIAL)
                    callIntent.data = Uri.parse("tel:${jobDetail.custom_link.replace("tel:", "")}")
                    startActivity(callIntent)
                }

                // Set up WhatsApp button to open WhatsApp with job's contact preference link
                binding.buttonWhatsApp.setOnClickListener {
                    val whatsappIntent = Intent(Intent.ACTION_VIEW)
                    whatsappIntent.data = Uri.parse(jobDetail.contact_preference.whatsapp_link)
                    startActivity(whatsappIntent)
                }
            } else {
                Log.e("Job Details", "JobDetail is null")
            }
        }

        // Fetch job details by ID
        jobId?.let {
            viewModel.fetchJobById(it)
        }
    }
    // Save job to repository
    fun onSaveJob(view: View) {
        viewModel.addJob(id, binding.textViewTitle.text.toString(),binding.textViewLocation.text.toString(),binding.textViewSalary.text.toString(),phone)
    }

    // Remove job from repository
    fun onRemoveJob(view: View) {
        viewModel.deleteJob(id)
        Toast.makeText(this,"Job Removed Successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}

