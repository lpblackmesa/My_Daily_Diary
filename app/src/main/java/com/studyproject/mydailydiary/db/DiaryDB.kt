package com.studyproject.mydailydiary.db

import android.content.Context
import androidx.room.Room

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