package com.studyproject.mydailydiary.data.entity

import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.HolderType

data class RecycleViewEntity(
    val id: Long,
    val type : HolderType,
    val data: DiaryItem?,
    val date : Long?
    )
