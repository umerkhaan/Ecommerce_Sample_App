package com.task.airlift_ecommerce_task.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.task.airlift_ecommerce_task.data.db.entities.CartItem
import com.task.airlift_ecommerce_task.data.db.entities.Product

@Dao
interface ProductDao : BaseDao<Product> {
    @Query("SELECT * FROM tbl_product ORDER BY _id DESC")
    suspend fun getAllItems(): List<Product>

    @Query("SELECT * FROM tbl_product WHERE _id = :id")
    suspend fun getProductById(id: Int): Product

    @Query("SELECT _id FROM tbl_product WHERE _id = :id")
    suspend fun checkIfProductExist(id: Int): Int?

    @Query("DELETE FROM tbl_product WHERE _id = :id")
    suspend fun removeProduct(id: Int)
}