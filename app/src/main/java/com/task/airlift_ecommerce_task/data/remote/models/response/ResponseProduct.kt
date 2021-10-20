package com.task.airlift_ecommerce_task.data.remote.models.response

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.task.airlift_ecommerce_task.data.db.entities.Product
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseProduct(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("price")
    val price: Double,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("rating")
    val rating: Rating? = null

) : Parcelable {
    @IgnoredOnParcel
    var cartQuantity: MutableLiveData<Int>? = null
        get() {
            if (field == null) {
                field = MutableLiveData(0)
            }

            return field
        }

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
    @field:SerializedName("rate")
    val rate: Double? = null,

    @field:SerializedName("count")
    val count: Int? = null
) : Parcelable
