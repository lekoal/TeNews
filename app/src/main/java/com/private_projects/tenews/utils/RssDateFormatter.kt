package com.private_projects.tenews.utils

import java.text.SimpleDateFormat
import java.util.*

class RssDateFormatter {
    private val inputFormatA = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
    private val inputFormatB = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    private val outputFormat = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())
    fun format(date: Date): String {
        return try {
            val inputDate = inputFormatA.parse(date.toString())
            val outputDate = inputDate?.let { outputFormat.format(it) }
            outputDate.toString()
        } catch (e: Exception) {
            val inputDate = inputFormatB.parse(date.toString())
            val outputDate = inputDate?.let { outputFormat.format(it) }
            outputDate.toString()
        }
    }
    fun toDateFormat(date: String): Date {
        return try {
            inputFormatA.parse(date) as Date
        } catch (e: Exception) {
            inputFormatB.parse(date) as Date
        }
    }
}