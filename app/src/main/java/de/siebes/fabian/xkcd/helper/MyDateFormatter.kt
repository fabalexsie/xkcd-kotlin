package de.siebes.fabian.xkcd.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object MyDateFormatter {
    private val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY)

    fun format(date: Date): String {
        return sdf.format(date)
    }
}