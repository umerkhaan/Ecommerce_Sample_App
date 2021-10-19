package com.task.airlift_ecommerce_task.data.db.typeConvertors

import androidx.room.TypeConverter
import java.util.*

object DateTypeConverter {
    @TypeConverter
    @JvmStatic
    fun toDate(long: Long?): Date? {
        return long?.let {
            Date(it)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}