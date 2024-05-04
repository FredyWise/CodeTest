package com.fredy.mysavingsscreens.Data.Database.Converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

open class DateTimeConverter {
    @TypeConverter
    fun toDateTime(dateTime: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(dateTime, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun fromDateTime(dateTime: LocalDateTime): Long {
        return dateTime.toEpochSecond(ZoneOffset.UTC)
    }
}