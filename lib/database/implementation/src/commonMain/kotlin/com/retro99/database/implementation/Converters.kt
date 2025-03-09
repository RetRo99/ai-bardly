package com.retro99.database.implementation

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    private val separator = ","

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(separator)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return if (value.isNullOrBlank()) null else value.split(separator).map { it.trim() }
    }
}