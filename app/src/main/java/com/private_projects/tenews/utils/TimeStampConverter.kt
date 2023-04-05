package com.private_projects.tenews.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.*

class TimestampConverter {
    fun convert(dateTime: Int): String {
        val calendar = Calendar.getInstance()
        val timeZone = calendar.timeZone
        val simpleDateFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ENGLISH)
        simpleDateFormat.timeZone = timeZone
        return simpleDateFormat.format(dateTime.toLong() * 1000L)
    }
}