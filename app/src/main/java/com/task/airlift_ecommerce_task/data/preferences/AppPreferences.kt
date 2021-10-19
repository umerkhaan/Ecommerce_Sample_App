package com.task.airlift_ecommerce_task.data.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferences @Inject constructor(
    @ApplicationContext
    context: Context
) : SharedPreferences(context, true) {
    companion object {
        const val PREF_TOKEN = "pref_token"
    }

    fun setToken(token: String) {
        setString(PREF_TOKEN, token)
    }

    fun getToken(): String {
        return getString(PREF_TOKEN, "")
    }
}