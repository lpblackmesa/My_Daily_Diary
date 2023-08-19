package com.studyproject.mydailydiary.data

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize
@Parcelize
@IgnoreExtraProperties
data class DiaryItem(
    val date: Long? = null,
    val mood: Int? = null,
    val doing: ArrayList<Int>? = null,
    val text: String? = null,
    val notification: Boolean? = null
): Parcelable {}

