package com.l.wikipediasearch.helper

import java.text.SimpleDateFormat
import java.util.*

object Utilities {
    @JvmStatic
    fun dateToString(date: Date?, format: String?): String {
        if (date == null) {
            return ""
        }
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}