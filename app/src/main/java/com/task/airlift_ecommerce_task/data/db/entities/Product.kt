package com.task.airlift_ecommerce_task.data.db.entities

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tbl_product")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "price")
    var price: Double,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "image")
    var image: String? = null,
    @ColumnInfo(name = "rating")
    var rating: Double? = null,
    @ColumnInfo(name = "ratingCount")
    var ratingCount: Int? = null
) : Parcelable {
    @Ignore
    @IgnoredOnParcel
    var cartQuantity: MutableLiveData<Int>? = null
        get() {
            if (field == null) {
                field = MutableLiveData(0)
            }

            return field
        }

}