package com.studyproject.mydailydiary.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
    object DiaryDB {

        var DiaryDAO: DiaryDAO? = null

        private var db: AppDataBase? = null

        //инициализация базы данных


        fun initDB(context : Context) {
            db = Room.databaseBuilder(context, AppDataBase::class.java, "data-base")
                .build()
            DiaryDAO = db?.getDiaryDao()
        }
}