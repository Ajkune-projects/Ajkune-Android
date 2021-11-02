package com.ajkune.professional.utilities.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData

class ConnectionLiveData (private val context: Context) : LiveData<Boolean>() {
    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (connectivityReceiverListener != null) {
                connectivityReceiverListener!!.onNetworkConnectionChange(context.isConnected)
            }
            postValue(context.isConnected)
        }
    }
    override fun onActive() {
        super.onActive()
        context.registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }
    override fun onInactive() {
        super.onInactive()
        try {
            context.unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
        }
    }
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChange(isConnected: Boolean)
    }
}
val Context.isConnected: Boolean
    get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnectedOrConnecting == true