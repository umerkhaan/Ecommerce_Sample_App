package com.task.airlift_ecommerce_task.ui.dataBinding.formattingUtils

object StringUtils {
    fun getPercentage(int: Int): String {
        return "$int %"
    }

    fun getPrice(double: Double): String {
        return "Rs. $double"
    }

    fun getPrice(double: Double, quantity: Int): String {
        return "Rs. $double x $quantity"
    }

    fun getTotalPrice(double: Double, quantity: Int): String {
        return "Total Rs. ${double * quantity}"
    }
}