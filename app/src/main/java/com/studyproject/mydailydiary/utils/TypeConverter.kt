package com.studyproject.mydailydiary.utils

import androidx.room.TypeConverter

class TypeConverter {
    // конвертер для записи Boolean в базу

    @TypeConverter
    fun intToBool(value : Int) : Boolean {
        return value == 1

    }
    fun boolToInt(value : Boolean) : Int {
        return if (value) 1
        else 0
    }

}