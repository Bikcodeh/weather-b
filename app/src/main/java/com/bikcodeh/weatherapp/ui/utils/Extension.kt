package com.bikcodeh.weatherapp.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.toReadableDate(
    locale: Locale = Locale.ENGLISH
): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", locale)
    val outputFormat = SimpleDateFormat("EEEE, dd MMMM", locale)

    val date: Date = inputFormat.parse(this) ?: return this
    return outputFormat.format(date)
}


fun String.toDayAbbreviation(
    locale: Locale = Locale.ENGLISH
): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", locale)
    val outputFormat = SimpleDateFormat("EEE", locale)

    val date = inputFormat.parse(this) ?: return ""
    return outputFormat.format(date).uppercase()
}