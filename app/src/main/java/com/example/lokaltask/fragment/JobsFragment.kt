package com.example.lokaltask.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lokaltask.adapter.JobsAdapter
import com.example.lokaltask.LokalJobApplication
import com.example.lokaltask.databinding.FragmentJobsBinding
import com.example.lokaltask.viewmodel.JobsViewModel
import com.example.lokaltask.viewmodel.JobsViewModelFactory

class JobsFragment : Fragment() {

    private lateinit var jobsAdapter: JobsAdapter // Adapter for the RecyclerView
    private lateinit var viewModel: JobsViewModel // ViewModel for handling data
    private var _binding: FragmentJobsBinding? = null // View binding for the fragment
    private val binding get() = _binding!! // Non-nullable reference to binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show progress bar and hide text view initially
        binding.progressBar.visibility = View.VISIBLE
        binding.text.visibility = View.GONE

        // Obtain the ViewModel from the ViewModelProvider
        val application = requireActivity().application as LokalJobApplication
        val repository = application.repository
        val factory = JobsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(JobsViewModel::class.java)

        // Set up the RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobsAdapter = JobsAdapter(emptyList(),requireContext()) // Initialize with empty list
        binding.recyclerView.adapter = jobsAdapter

        // Observe changes in the jobs LiveData
        viewModel.jobs.observe(viewLifecycleOwner, Observer { jobResponse ->

            if (jobResponse != null) {
                // Check if there are jobs in the response
                if(jobResponse.results.size==0){
                    binding.text.visibility = View.VISIBLE // Show no data message if empty
                }
                else{
                    binding.text.visibility = View.GONE // Hide message if there are jobs
                }
                // Update the adapter with new job data
                jobsAdapter.updateJobs(jobResponse.results)
            } else {
                Log.d("Update Job", "No data received") // Log message if no data is received
            }
            // Hide the progress bar after data is processed
            binding.progressBar.visibility = View.GONE
        })
        // Trigger the ViewModel to fetch jobs
        viewModel.fetchJobs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding reference to avoid memory leaks
        _binding = null
    }
}
