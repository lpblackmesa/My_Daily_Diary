package com.studyproject.mydailydiary.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.studyproject.mydailydiary.data.entity.DiaryItemEntity

@Dao
interface DiaryDAO {
    //интерфейс запросов в базу данных
    //функции suspend для работы их в потоке
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiaryItem(DiaryItem: DiaryItemEntity)

    @Delete
    suspend fun delDiaryItem(DiaryItem: DiaryItemEntity)

    @Query("SELECT * FROM DiaryItem")
    suspend fun getDiary(): List<DiaryItemEntity>

}