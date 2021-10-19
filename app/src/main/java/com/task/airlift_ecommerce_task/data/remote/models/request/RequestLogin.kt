package com.task.airlift_ecommerce_task.data.remote.models.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestLogin(
    val username: String,
    val password: String
) : Parcelable
