package com.task.airlift_ecommerce_task.utils

import android.content.Context
import android.widget.Toast
import android.view.Gravity
import android.view.View
import android.widget.TextView

object SingleToastUtil {
    private var toast: Toast? = null

    fun show(context: Context, text: String, duration: Int) {
        toast?.cancel()

        toast = Toast.makeText(context, text, duration)

        if (!ApiVersionUtil.isR) {
            val v = toast?.view?.findViewById<View>(android.R.id.message) as TextView
            v.gravity = Gravity.CENTER
        }

        toast?.show()
    }

    fun show(context: Context, textResource: Int, duration: Int) {
        toast?.cancel()

        toast = Toast.makeText(context, textResource, duration)

        if (!ApiVersionUtil.isR) {
            val v = toast?.view?.findViewById<View>(android.R.id.message) as TextView
            v.gravity = Gravity.CENTER
        }

        toast?.show()
    }

    fun cancel() {
        toast?.cancel()
    }
}
