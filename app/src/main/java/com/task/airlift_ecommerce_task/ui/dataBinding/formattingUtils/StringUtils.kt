package com.task.airlift_ecommerce_task.ui.dataBinding.formattingUtils

object StringUtils {
    fun getPercentage(int: Int): String {
        return "$int %"
    }

    fun getPrice(double: Double): String {
        return "Rs. $double"
    }
}