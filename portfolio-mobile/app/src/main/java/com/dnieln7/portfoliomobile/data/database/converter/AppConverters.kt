package com.dnieln7.portfoliomobile.data.database.converter

import androidx.room.TypeConverter

class AppConverters {

    @TypeConverter
    fun fromStringToStringList(value: String): List<String> {
        return value.split("|")
    }

    @TypeConverter
    fun fromStringListToString(value: List<String>): String {
        return value.joinToString(separator = "|")
    }
}
