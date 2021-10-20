package com.task.airlift_ecommerce_task.ui.dataBinding.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.ListenerUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.task.airlift_ecommerce_task.R


object SwipeRefreshBindingAdapters {
    @BindingAdapter("app:isRefreshing")
    @JvmStatic
    fun setIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

    @BindingAdapter("app:enable")
    @JvmStatic
    fun setEnable(swipeRefreshLayout: SwipeRefreshLayout, isEnable: Boolean) {
        swipeRefreshLayout.isEnabled = isEnable
    }

    @InverseBindingAdapter(attribute = "app:isRefreshing", event = "app:isRefreshingAttrChanged")
    @JvmStatic
    fun getIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean {
        return swipeRefreshLayout.isRefreshing
    }

    @BindingAdapter(
        value = ["onRefreshListener", "app:isRefreshingAttrChanged"],
        requireAll = false
    )
    @JvmStatic
    fun setOnRefreshListener(
        swipeRefreshLayout: SwipeRefreshLayout,
        listener: OnRefreshListener?,
        inverseBindingListener: InverseBindingListener?
    ) {
        val newValue = OnRefreshListener {
            listener?.onRefresh()
            inverseBindingListener?.onChange()
        }

        val oldValue =
            ListenerUtil.trackListener(swipeRefreshLayout, newValue, R.id.onSwipeRefreshListener)
        if (oldValue != null) {
            swipeRefreshLayout.setOnRefreshListener(null)
        }
        swipeRefreshLayout.setOnRefreshListener(newValue)
    }
}