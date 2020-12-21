package com.shadows.bitsodream.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class MyInternetMonitoringService(private val connectivityChangesCallback: ConnectivityChangesCallback): ConnectivityManager.NetworkCallback() {

    private lateinit var networkRequest: NetworkRequest

    fun connectionStateMonitor() {
        networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .build()
    }

    fun enable(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun disable(context: Context){
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(this)
    }


    override fun onAvailable(network: Network?) {
        connectivityChangesCallback.onChanged(true)

    }


    override fun onLost(network: Network) {
        connectivityChangesCallback.onChanged(false)

    }

    override fun onUnavailable() {
        connectivityChangesCallback.onChanged(false)

    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        connectivityChangesCallback.onChanged(false)

    }



}