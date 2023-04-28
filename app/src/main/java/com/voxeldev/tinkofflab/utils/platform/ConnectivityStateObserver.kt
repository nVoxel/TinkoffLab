package com.voxeldev.tinkofflab.utils.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Handler
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityStateObserver @Inject constructor(
    @ApplicationContext context: Context,
    private val networkHandler: NetworkHandler,
) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
        .build()

    private val networkCallback = object : NetworkCallback() {

        override fun onAvailable(network: Network) {
            onAvailable?.invoke()
        }

        override fun onLost(network: Network) {
            onLost?.invoke()
        }
    }

    private var onAvailable: (() -> Unit)? = null

    private var onLost: (() -> Unit)? = null

    fun registerNetworkCallback(
        onAvailable: () -> Unit = { },
        onLost: () -> Unit,
        handler: Handler
    ) {
        val firstCall = this.onAvailable == null && this.onLost == null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.onAvailable = onAvailable
            this.onLost = onLost
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback, handler)
        } else {
            this.onAvailable = {
                handler.post {
                    onAvailable()
                }
            }
            this.onLost = {
                handler.post {
                    onLost()
                }
            }
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        }
        if (firstCall && !networkHandler.isNetworkAvailable())
            onLost()
    }

    fun unregisterNetworkCallback() {
        this.onAvailable = { }
        this.onLost = { }
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
