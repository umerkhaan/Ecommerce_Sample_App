package com.task.airlift_ecommerce_task.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/*
 * Created by SDD-17 on 25-Apr-19.
 */

object TimeDateUtil {
    enum class Format(val format: String) {
        DATE("yyyy-MM-dd"), DATE_DISPLAY("dd MMM yyyy"), TIME("hh:mm a"), DATE_TIME("dd MMM yyyy - hh:mm a"), FULL(
            "EEEE dd MMMM, yyyy"
        ), UNIVERSAL("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    }

    fun getDate(millis: Long, format: Format): String {
        val simpleDateFormat = SimpleDateFormat(format.format, Locale.US)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis

        return simpleDateFormat.format(calendar.time)
    }

    fun getDate(d: String, format: Format): Date? {
        val simpleDateFormat = SimpleDateFormat(format.format, Locale.US)

        var date: Date? = null

        try {
            date = simpleDateFormat.parse(d)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun getCurrentMonthStartEndDate(format: Format): String? {
        val simpleDateFormat = SimpleDateFormat(format.format, Locale.US)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        val monthFirstDay = calendar.time
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val monthLastDay = calendar.time

        return "${simpleDateFormat.format(monthFirstDay)} - ${simpleDateFormat.format(monthLastDay)}"
    }

    fun getTime(): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss a", Locale.US)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        return simpleDateFormat.format(calendar.time)
    }

    fun getTimeForCountDown(millis:Long):String{
        val simpleDateFormat = SimpleDateFormat("mm:ss", Locale.US)

        return simpleDateFormat.format(millis)
    }

    fun isDayTime(): Boolean {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)

        return hour in 7..17
    }

    fun parseTimeFromMilliseconds(timeInMilliSeconds: Long): String {
        val result: String
        val stringBuilder = StringBuilder()
        val formatter = Formatter(stringBuilder, Locale.getDefault())

        val totalSeconds = timeInMilliSeconds / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600

        stringBuilder.setLength(0)

        result = if (hours > 0) {
            formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        } else {
            formatter.format("%02d:%02d", minutes, seconds).toString()
        }

        return result
    }

    fun parseTimeFromMilliseconds(timeInMilliSeconds: String?): String? {
        var result: String? = null
        val stringBuilder = StringBuilder()
        val formatter = Formatter(stringBuilder, Locale.getDefault())
        try {
            if (timeInMilliSeconds == null) {
                result = ""
            } else {
                val millis = java.lang.Long.parseLong(timeInMilliSeconds.trim { it <= ' ' })
                val totalSeconds = millis / 1000
                val seconds = totalSeconds % 60
                val minutes = totalSeconds / 60 % 60
                val hours = totalSeconds / 3600

                stringBuilder.setLength(0)

                result = if (hours > 0) {
                    formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
                } else {
                    formatter.format("%02d:%02d", minutes, seconds).toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun getDayMillis(): Long {
        return (24 * 60 * 60 * 1000).toLong()
    }

    fun getYesterdayMillis(): Long {
        val yesterday = Date(System.currentTimeMillis() - getDayMillis())
        return yesterday.time
    }

    fun getPreviousDate(inputDate: Long, timeDateFormat: Format): String {
        val format = SimpleDateFormat(timeDateFormat.format, Locale.US)

        val c = Calendar.getInstance()
        c.timeInMillis = inputDate
        c.add(Calendar.YEAR, -3)

        return format.format(c.time)
    }

    fun getCurrentYear(): Int {
        val year = getDate(System.currentTimeMillis(), Format.DATE)
        val i = year.indexOf('-')
        return Integer.parseInt(year.substring(0, i))
    }


    fun convertDateToWords(deliveryDate: String): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        var d: Date? = null
        try {
            d = simpleDateFormat.parse(deliveryDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val dateFormat = SimpleDateFormat("EEEE dd MMMM, yyyy", Locale.US)
        return dateFormat.format(d)
    }

    fun getMonthNumber(s: String): Int {
        val months = arrayOf(
            "january,",
            "february,",
            "march,",
            "april,",
            "may,",
            "june,",
            "july,",
            "august,",
            "september,",
            "october,",
            "november,",
            "december,"
        )

        for (i in months.indices) {
            if (s.equals(months[i], ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    fun convertDateToNumbers(deliveryDate: String): String {
        val simpleDateFormat = SimpleDateFormat("EEEE dd MMMM, yyyy", Locale.US)
        var d: Date? = null
        try {
            d = simpleDateFormat.parse(deliveryDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return dateFormat.format(d)
    }

    private fun clearTimes(c: Calendar): Calendar {
        c[Calendar.HOUR_OF_DAY] = 0
        c[Calendar.MINUTE] = 0
        c[Calendar.SECOND] = 0
        c[Calendar.MILLISECOND] = 0
        return c
    }

    fun convertDateToGroups(millis: Long): String {
        var today = Calendar.getInstance()
        today = clearTimes(today)
        var yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        yesterday = clearTimes(yesterday)
        var last7days = Calendar.getInstance()
        last7days.add(Calendar.DAY_OF_YEAR, -7)
        last7days = clearTimes(last7days)
        var last30days = Calendar.getInstance()
        last30days.add(Calendar.DAY_OF_YEAR, -30)
        last30days = clearTimes(last30days)

        return when {
            millis > today.timeInMillis -> {
                "Today"
            }
            millis > yesterday.timeInMillis -> {
                "Yesterday"
            }
            millis > last7days.timeInMillis -> {
                "Last 7 days"
            }
            millis > last30days.timeInMillis -> {
                "Last 30 days"
            }
            else -> {
                "More than 30 days"
            }
        }
    }
}