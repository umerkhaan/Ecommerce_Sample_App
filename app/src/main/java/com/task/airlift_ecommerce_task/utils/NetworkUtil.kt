package com.task.airlift_ecommerce_task.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/*
 * Created by SDD-17 on 25-Apr-19.
 */

object NetworkUtil {
    private const val TYPE_WIFE = 1
    private const val TYPE_MOBILE = 2
    private const val TYPE_NOT_CONNECTED = 0

    private fun getConnectivityStatus(context: Context): Int {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (ApiVersionUtil.isMarshmallow) {
            val networkCapabilities =
                connectivityManager.activeNetwork
                    ?: return TYPE_NOT_CONNECTED
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities)
                    ?: return TYPE_NOT_CONNECTED

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> TYPE_MOBILE
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> TYPE_WIFE
                else -> TYPE_NOT_CONNECTED
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    return TYPE_WIFE
                }
                if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    return TYPE_MOBILE
                }
            }
            return TYPE_NOT_CONNECTED
        }
    }

    fun getConnectivityStatusString(context: Context): String? {
        val conn = getConnectivityStatus(context)
        var status: String? = null

        when (conn) {
            TYPE_WIFE -> status = "Wi-Fi Enabled."
            TYPE_MOBILE -> status = "Mobile Data Enabled"
            TYPE_NOT_CONNECTED -> status = "Not Connected To Internet."
        }

        return status
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        if (ApiVersionUtil.isMarshmallow) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo != null &&
                    connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting
        }
    }
}
