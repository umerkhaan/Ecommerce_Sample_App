package com.task.airlift_ecommerce_task.data.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.task.airlift_ecommerce_task.data.db.entities.Category

@Dao
interface CategoryDao : BaseDao<Category> {
    @Query("SELECT * FROM tbl_category ORDER BY _id DESC")
    suspend fun getAllCategories(): List<Category>

    @Query("DELETE FROM tbl_category")
    suspend fun deleteAllRecords()
}