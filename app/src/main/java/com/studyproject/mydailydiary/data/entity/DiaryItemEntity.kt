package com.studyproject.mydailydiary.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DiaryItem")
//класс для создания таблицы через room в базе данных с полями
data class DiaryItemEntity  (
    @PrimaryKey
    @ColumnInfo("Date")
    val date : Long?,
    @ColumnInfo("Mood")
    val mood: Int?,
    @ColumnInfo("Doing")
    val doing: ArrayList<Int>?,
    @ColumnInfo("Text")
    val text: String?,
    @ColumnInfo("Notification")
    val notification: Boolean?

)