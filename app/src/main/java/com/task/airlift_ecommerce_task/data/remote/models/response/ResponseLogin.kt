package com.task.airlift_ecommerce_task.data.remote.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseLogin(
	@field:SerializedName("token")
	val token: String? = null
) : Parcelable
