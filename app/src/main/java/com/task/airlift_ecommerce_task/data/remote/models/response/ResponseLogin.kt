package com.task.airlift_ecommerce_task.data.remote.models.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseLogin(
	val token: String? = null
) : Parcelable
