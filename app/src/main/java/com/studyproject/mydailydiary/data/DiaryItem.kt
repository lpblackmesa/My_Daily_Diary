package com.studyproject.mydailydiary.data

data class DiaryItem(
    val date: Long,
    val mood: Int,
    val doing: ArrayList<Int>,
    val text: String,
    val notification: Boolean
    ) {
    override fun toString(): String = date.toString() + mood.toString() + doing.toString() + text

}