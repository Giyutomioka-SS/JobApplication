package com.example.lokaltask.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lokaltask.adapter.JobsAdapter2
import com.example.lokaltask.LokalJobApplication
import com.example.lokaltask.databinding.FragmentBookmarksBinding
import com.example.lokaltask.viewmodel.JobsViewModel
import com.example.lokaltask.viewmodel.JobsViewModelFactory

class BookmarksFragment : Fragment() {

    // Adapter for displaying job items
    private lateinit var jobsAdapter: JobsAdapter2

    // ViewModel for managing job data
    private lateinit var viewModel: JobsViewModel

    // ViewBinding for accessing UI elements
    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show progress bar and hide text initially
        binding.progressBar.visibility = View.VISIBLE
        binding.text.visibility = View.GONE

        // Obtain the ViewModel from the ViewModelProvider
        val application = requireActivity().application as LokalJobApplication
        val repository = application.repository
        val factory = JobsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[JobsViewModel::class.java]

        // Set up RecyclerView with LinearLayoutManager and JobsAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        jobsAdapter = JobsAdapter2(emptyList(),requireContext()) // Initialize with empty list
        binding.recyclerView.adapter = jobsAdapter

        // Observe changes in savedJobs LiveData from ViewModel
        viewModel.savedJobs.observe(viewLifecycleOwner, Observer { jobResponse ->

            // Show or hide text based on whether jobResponse is empty
            if (jobResponse != null) {

                if(jobResponse.isEmpty()){
                    binding.text.visibility = View.VISIBLE
                }
                else{
                    binding.text.visibility = View.GONE
                }
                // Update the adapter with the latest job data
                jobsAdapter.updateJobs(jobResponse)

            } else {
                Log.d("Update Job", "No data received")
            }
            // Hide progress bar after data is loaded
            binding.progressBar.visibility = View.GONE
        })
        // Fetch saved jobs from the ViewModel
        viewModel.getSavedJobs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear binding reference to avoid memory leaks
        _binding = null
    }
}