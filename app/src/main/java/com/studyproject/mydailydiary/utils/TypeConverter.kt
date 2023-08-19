package com.studyproject.mydailydiary.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    // конвертер для записи Boolean и ArrayList<Int> в базу

    @TypeConverter
    fun intToBool(value: Int): Boolean {
        return value == 1
    }
    @TypeConverter
    fun boolToInt(value: Boolean): Int {
        return if (value) 1
        else 0
    }
    @TypeConverter
    fun intArrayToString(value: ArrayList<Int>?): String {

        return if (value!=null) Gson().toJson(value) else ""
    }
    @TypeConverter
    fun stringToIntArray(value: String): ArrayList<Int> {
        return Gson().fromJson(value, object : TypeToken<ArrayList<Int>>() {}.type)?:arrayListOf()
    }

}
