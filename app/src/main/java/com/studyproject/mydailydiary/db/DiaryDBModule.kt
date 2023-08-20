package com.studyproject.mydailydiary.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//откуда будет установлено
// активити - фрагмент - SingletonComponent - доступ отовсюду, будет установлен в APP
@InstallIn(SingletonComponent::class)

object DiaryDBModule {

    //в DAO модуль мы не можем сделать @Inject constructor, так как он не наш
    //@Module , @Provides делает правило, по которому оно может создать и Inject закрытые классы

    @Provides
    //указываем, делать ли новую инстанцию
    @Singleton
    fun provideDiaryDao(@ApplicationContext context: Context): DiaryDAO {
        return Room.databaseBuilder(context, AppDataBase::class.java, "data-base")
            .build().getDiaryDao()
    }
}