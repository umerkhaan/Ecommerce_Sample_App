package com.task.airlift_ecommerce_task.data.remote.models.response

import android.os.Parcelable
import com.task.airlift_ecommerce_task.data.db.entities.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String? = null,
    val category: String,
    val image: String? = null,
    val rating: Rating? = null
) : Parcelable {
    fun toProduct(): Product {
        return Product(
            id,
            title,
            price,
            description,
            category,
            image,
            rating?.rate,
            rating?.count
        )
    }
}

@Parcelize
data class Rating(
    val rate: Double? = null,
    val count: Int? = null
) : Parcelable
