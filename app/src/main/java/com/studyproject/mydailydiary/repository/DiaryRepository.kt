package com.studyproject.mydailydiary.repository

import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.entity.DiaryItemEntity
import com.studyproject.mydailydiary.db.DiaryDB

class DiaryRepository {


    suspend fun getDiary(): ArrayList<DiaryItem> {
        return (DiaryDB.diaryDAO?.getDiary()?.map {
            DiaryItem(
                it.date,
                it.mood,
                it.doing,
                it.text,
                it.notification
            )
        } as? ArrayList<DiaryItem>) ?: arrayListOf()
    }

    suspend fun getDiaryItem(diaryItem: DiaryItem): DiaryItem {
        return DiaryDB.diaryDAO?.getDiaryItem(diaryItem.date)?.let {
            DiaryItem(
                it.date,
                it.mood,
                it.doing,
                it.text,
                it.notification
            )
        } ?: DiaryItem(0,0,arrayListOf(),"",false)
    }


    suspend fun addDiary(diaryItem: DiaryItem): Boolean {
        DiaryDB.diaryDAO?.insertDiaryItem(
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
        DiaryDB.diaryDAO?.delDiaryItem(
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