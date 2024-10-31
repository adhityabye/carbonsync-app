package io.apaaja.carbonsync.utils.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateConverter {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, formatter) }
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(formatter)
    }
}