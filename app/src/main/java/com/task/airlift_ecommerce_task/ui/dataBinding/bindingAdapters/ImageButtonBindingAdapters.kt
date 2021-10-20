package com.task.airlift_ecommerce_task.ui.dataBinding.bindingAdapters

import android.widget.ImageButton
import androidx.databinding.BindingAdapter

object ImageButtonBindingAdapters {
    @BindingAdapter("app:src")
    @JvmStatic
    fun setImageResource(imageButton: ImageButton, resourceId: Int) {
        if (resourceId != 0) {
            imageButton.setImageResource(resourceId)
        }
    }
}