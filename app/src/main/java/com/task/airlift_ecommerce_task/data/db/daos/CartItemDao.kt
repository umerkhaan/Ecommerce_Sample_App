package com.task.airlift_ecommerce_task.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.task.airlift_ecommerce_task.data.db.entities.CartItem
import com.task.airlift_ecommerce_task.data.db.entities.Category

@Dao
interface CartItemDao : BaseDao<CartItem> {
    @Query("SELECT COUNT(_id) FROM tbl_cart")
    suspend fun getCartItemsCount(): Int

    @Query("SELECT * FROM tbl_cart ORDER BY _id DESC")
    suspend fun getAllItems(): List<CartItem>

    @Query("SELECT * FROM tbl_cart WHERE productId = :id")
    suspend fun getCartItemByProductId(id: Int): CartItem?

    @Query("DELETE FROM tbl_cart WHERE productId = :id")
    suspend fun removeProductFromCart(id: Int)

    @Query("DELETE FROM tbl_cart")
    suspend fun deleteAllRecords()
}