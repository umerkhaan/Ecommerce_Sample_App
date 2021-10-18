package com.app.realtec.pos.data.room.typeConvertors

import androidx.room.TypeConverter
import java.util.*

/*
 * Created by SDD-17 on 04-Dec-19.
 */

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