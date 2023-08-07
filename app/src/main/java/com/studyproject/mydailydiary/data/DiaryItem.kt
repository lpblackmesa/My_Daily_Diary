package com.studyproject.mydailydiary.data

data class DiaryItem(
    val date: Long,
    val mood: Int,
    val doing: String,
    val text: String,
    val notification: Boolean

)
