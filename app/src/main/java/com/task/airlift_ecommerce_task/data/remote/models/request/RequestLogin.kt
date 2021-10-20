package com.task.airlift_ecommerce_task.data.remote.models.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestLogin(
	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable
