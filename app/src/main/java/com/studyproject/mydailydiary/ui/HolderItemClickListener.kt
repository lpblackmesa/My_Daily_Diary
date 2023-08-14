package com.studyproject.mydailydiary.ui

import com.studyproject.mydailydiary.data.DiaryItem

interface HolderItemClickListener {
    fun onCHolderItemlick(item : DiaryItem)
    fun onHolderItemLongClick(item : DiaryItem)
}

