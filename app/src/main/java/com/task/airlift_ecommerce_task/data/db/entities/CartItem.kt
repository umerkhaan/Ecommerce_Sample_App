package com.task.airlift_ecommerce_task.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "tbl_cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Int = 0,
    @ColumnInfo(name = "productId")
    var productId: Int,
    @ColumnInfo(name = "quantity")
    var quantity: Int,
    @ColumnInfo(name = "quantity")
    var dateTime: Date
) : Parcelable