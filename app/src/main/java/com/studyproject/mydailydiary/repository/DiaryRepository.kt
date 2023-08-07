package com.studyproject.mydailydiary.repository

import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.entity.DiaryItemEntity
import com.studyproject.mydailydiary.db.DiaryDB

class DiaryRepository {


    suspend fun getDiary(): ArrayList<DiaryItem> {
        return (DiaryDB.DiaryDAO?.getDiary()?.map {
            DiaryItem(
                it.date,
                it.mood,
                it.doing,
                it.text,
                it.notification
            )
        } as? ArrayList<DiaryItem>) ?: arrayListOf()
    }

    suspend fun addDiary(diaryItem: DiaryItem): Boolean {
        DiaryDB.DiaryDAO?.insertDiaryItem(
            DiaryItemEntity(
                diaryItem.date,
                diaryItem.mood,
                diaryItem.doing,
                diaryItem.text,
                diaryItem.notification
            )
        )
        return true
    }

    suspend fun delDiary(diaryItem: DiaryItem) {
        DiaryDB.DiaryDAO?.delDiaryItem(
            DiaryItemEntity(
                diaryItem.date,
                diaryItem.mood,
                diaryItem.doing,
                diaryItem.text,
                diaryItem.notification
            )
        )
    }
}