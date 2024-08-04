package com.example.lokaltask.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lokaltask.R
import com.example.lokaltask.fragment.BookmarksFragment
import com.example.lokaltask.fragment.JobsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the BottomNavigationView in the layout
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set a listener to handle navigation item clicks
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_jobs -> {
                    loadFragment(JobsFragment())  // Load JobsFragment when 'Jobs' is selected
                    true
                }
                R.id.navigation_bookmarks -> {
                    loadFragment(BookmarksFragment()) // Load BookmarksFragment when 'Bookmarks' is selected
                    true
                }
                else -> false
            }
        }

        // Load the default fragment (JobsFragment) if there is no saved instance state
        if (savedInstanceState == null) {
            loadFragment(JobsFragment())
        }
    }

    // Helper method to load a fragment into the nav_host_fragment container
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment) // Replace the current fragment
            .commit() // Commit the transaction
    }
}
