package com.bikcodeh.weatherapp.feature.detail.util

import android.text.format.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

/**
 * Long date with the best pattern for [locale], e.g. "Tuesday, December 16" (en-US) or
 * "martes, 16 de diciembre" (es).
 */
internal fun LocalDate.toFullDateText(locale: Locale): String {
    val pattern = DateFormat.getBestDateTimePattern(locale, "EEEEMMMMd")
    return DateTimeFormatter.ofPattern(pattern, locale).format(this)
}

/** Upper-cased abbreviated day of the week, e.g. "WED". */
internal fun LocalDate.toShortDayText(locale: Locale): String =
    dayOfWeek.getDisplayName(TextStyle.SHORT, locale).uppercase(locale)
