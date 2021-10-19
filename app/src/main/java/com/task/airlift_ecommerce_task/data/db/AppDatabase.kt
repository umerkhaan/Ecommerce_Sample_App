package com.task.airlift_ecommerce_task.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.airlift_ecommerce_task.data.db.typeConvertors.DateTypeConverter
import com.task.airlift_ecommerce_task.data.db.daos.CartItemDao
import com.task.airlift_ecommerce_task.data.db.daos.CategoryDao
import com.task.airlift_ecommerce_task.data.db.daos.ProductDao
import com.task.airlift_ecommerce_task.data.db.entities.CartItem
import com.task.airlift_ecommerce_task.data.db.entities.Category
import com.task.airlift_ecommerce_task.data.db.entities.Product
import com.task.airlift_ecommerce_task.utils.misc.Constants

@Database(
    entities = [
        Category::class,
        Product::class,
        CartItem::class
    ],
    version = Constants.DATABASE_VERSION
)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getProductDao(): ProductDao
    abstract fun getCartItemDao(): CartItemDao
}