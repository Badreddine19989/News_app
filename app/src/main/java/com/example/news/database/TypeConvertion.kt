package com.example.news.database

import androidx.room.TypeConverter

class TypeConvertion {

    @TypeConverter
    fun SourcetoString(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun StringtoSource(string: String): Source {
        return Source(string, string)
    }
}