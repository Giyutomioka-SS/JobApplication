package com.example.lokaltask.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// Utility class to check network status
class NetworkUtils {
    companion object{

        // Checks if the internet is available on the device.
        fun isInternetAvailable(context: Context): Boolean {

            // Get the ConnectivityManager from the system services
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {

                // Check if the active network has the capability to access the internet
                return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                ) ?: false // Return false if network capabilities are null
            }
        }
    }
}