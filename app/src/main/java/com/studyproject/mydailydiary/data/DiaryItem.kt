package com.studyproject.mydailydiary.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class DiaryItem(
    val date: Long,
    val mood: Int,
    val doing: ArrayList<Int>,
    val text: String,
    val notification: Boolean
): Parcelable {}

