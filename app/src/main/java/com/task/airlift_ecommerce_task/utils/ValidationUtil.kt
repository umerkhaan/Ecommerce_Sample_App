package com.app.realtec.pos.utils

import java.util.regex.Pattern
import android.text.TextUtils


object ValidationUtil {
    private const val IP_ADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"

    fun isValidIp(ipAddress: String): Boolean {
        val pattern = Pattern.compile(IP_ADDRESS_PATTERN)

        val matcher = pattern.matcher(ipAddress)
        return matcher.matches()
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}