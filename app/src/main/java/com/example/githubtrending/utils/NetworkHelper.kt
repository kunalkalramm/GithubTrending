package com.example.githubtrending.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log

class NetworkHelper(private val connectivityManager: ConnectivityManager) {

    private val networkCallbackListener by lazy { NetworkCallbackListener() }
    private val listeners by lazy { mutableListOf<NetworkListener>() }
    private var isRegistered = false

    fun registerNetworkListener(networkListener: NetworkListener) {
        if(!listeners.contains(networkListener))
            listeners.add(networkListener)

        if(!isRegistered) {
            connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallbackListener)
            isRegistered = true
        }
    }

    fun deregisterNetworkListener(networkListener: NetworkListener) {
        if(listeners.contains(networkListener))
            listeners.remove(networkListener)
    }

    inner class NetworkCallbackListener : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            informListeners(true, network)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            informListeners(false, network)
        }

        private fun informListeners(available: Boolean, network: Network?) {
            listeners.forEach {
                it.onAvailability(available, network)
            }
        }
    }

    interface NetworkListener {
        fun onAvailability(isAvailable: Boolean, network: Network?)
    }
}