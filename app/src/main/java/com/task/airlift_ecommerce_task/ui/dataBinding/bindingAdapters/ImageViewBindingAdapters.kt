package com.task.airlift_ecommerce_task.ui.dataBinding.bindingAdapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.task.airlift_ecommerce_task.R
import com.task.airlift_ecommerce_task.applications.GlideApp

object ImageViewBindingAdapters {
    // TODO: Properly Handle the null Url for Glide

    @BindingAdapter("app:src")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, imageUrl: String?) {
        GlideApp
            .with(imageView.context)
            .load(imageUrl ?: "")
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_image)
            .into(imageView)
    }

    @BindingAdapter("app:src")
    @JvmStatic
    fun setImageDrawable(imageView: ImageView, imageDrawable: Drawable?) {
        if (imageDrawable != null) {
            GlideApp
                .with(imageView.context)
                .load(imageDrawable)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image)
                .into(imageView)
        }
    }

    @BindingAdapter("app:src")
    @JvmStatic
    fun setImageResource(imageView: ImageView, resourceId: Int) {
        if (resourceId != 0) {
            imageView.setImageResource(resourceId)
        }
    }
}