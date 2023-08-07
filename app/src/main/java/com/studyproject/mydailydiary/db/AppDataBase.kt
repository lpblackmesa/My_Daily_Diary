package com.studyproject.mydailydiary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.studyproject.mydailydiary.data.entity.DiaryItemEntity
import com.studyproject.mydailydiary.utils.TypeConverter

//Указываем класс Entity в базе и версию базы данных
@Database(entities = [DiaryItemEntity::class], version = 1)
// конвертер для записи Boolean в базу
@TypeConverters(TypeConverter::class)
abstract class AppDataBase : RoomDatabase(){

    //функция создания самой базы данных
    abstract fun getDiaryDao(): DiaryDAO
}