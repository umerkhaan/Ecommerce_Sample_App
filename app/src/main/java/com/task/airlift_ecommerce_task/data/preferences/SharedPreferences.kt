package com.task.airlift_ecommerce_task.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

abstract class SharedPreferences(
    context: Context, isDefaultPreference: Boolean
) {
    private val fileName = "preferences"
    private var preferences = if (isDefaultPreference) {
        PreferenceManager.getDefaultSharedPreferences(context)
    } else {
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    // Delete
    fun delete(key: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }

    // Boolean
    fun setBoolean(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        if (!preferences.contains(key)) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putBoolean(key, defaultValue)
            editor.apply()
        }

        return preferences.getBoolean(key, defaultValue)
    }

    // String
    fun setString(key: String, value: String) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String): String {
        if (!preferences.contains(key)) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString(key, defaultValue)
            editor.apply()
        }

        return preferences.getString(key, defaultValue)!!
    }

    // Int
    fun setInt(key: String, value: Int) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        if (!preferences.contains(key)) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putInt(key, defaultValue)
            editor.apply()
        }

        return preferences.getInt(key, defaultValue)
    }

    // Long
    fun setLong(key: String, value: Long) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        if (!preferences.contains(key)) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putLong(key, defaultValue)
            editor.apply()
        }

        return preferences.getLong(key, defaultValue)
    }

    // Float
    fun setFloat(key: String, value: Float) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        if (!preferences.contains(key)) {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putFloat(key, defaultValue)
            editor.apply()
        }

        return preferences.getFloat(key, defaultValue)
    }
}