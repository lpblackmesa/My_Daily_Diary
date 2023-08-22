package com.studyproject.mydailydiary.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.entity.DiaryItemEntity
import com.studyproject.mydailydiary.db.DiaryDAO
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class DiaryRepository @Inject constructor(
    private val diaryDAO: DiaryDAO
    , private val database: FirebaseDatabase
    , private val auth : FirebaseAuth
) {


    suspend fun getDiaryFromFireBase(): ArrayList<DiaryItem> {
        //создание пустого списка DiaryItem
        val arrayDiaryItem: ArrayList<DiaryItem> = arrayListOf()
        //если есть залогиненый пользователь
        auth.currentUser?.let { user ->
            //заходим в папку пользователя
            val myRef = database.getReference(user.uid)
            //получение с ожиданием результата
            //для каждой подпапки добавляем ее содержимое в БД и в список
            myRef.get().await().children.forEach { snapshot ->
                snapshot.getValue<DiaryItem>()?.let { item ->
                    addDiary(item)
                    arrayDiaryItem.add(item)
                }
            }
        }
        return arrayDiaryItem
    }

    //запись массива данных в Firebase
    suspend fun setDiaryToFireBase(diary: ArrayList<DiaryItem>) {
        auth.currentUser?.let {
            val myRef = database.getReference(it.uid)
            val map = emptyMap<String, DiaryItem>().toMutableMap()
            diary.forEach { item ->
                if (item.date != null) {
                    map[item.date.toString()] = item
                }
            }
            myRef.updateChildren(map as Map<String, Any>)
        }
    }

    //запись одного item в Firebase
    suspend fun setDiaryToFireBase(diaryItem: DiaryItem) {
        //если есть Юзер
        auth.currentUser?.let { user ->
            //записываем в ветку Айди Юзер
            val myRef = database.getReference(user.uid)
            val myKey = diaryItem.date?.toString()
            myKey?.let { myRef.child(myKey).setValue(diaryItem) }
        }
    }

    //удаление одного item в Firebase
    suspend fun delDiaryFromFireBase(diaryItem: DiaryItem) {
        //если есть Юзер
        auth.currentUser?.let { user ->
            //записываем в ветку Айди Юзер
            val myRef = database.getReference(user.uid)
            val myKey = diaryItem.date?.toString()
            myKey?.let { myRef.child(myKey).removeValue() }
        }
    }

    suspend fun delDiaryFromFireBase(diary: ArrayList<DiaryItem>) {
        auth.currentUser?.let {
            val myRef = database.getReference(it.uid)
            val map = emptyMap<String, Any?>().toMutableMap()
            diary.forEach { item ->
                if (item.date != null) {
                    map[item.date.toString()] = null
                }
            }
            myRef.updateChildren(map as Map<String, Any?>)
        }
    }


    suspend fun getDiary(): ArrayList<DiaryItem> {
        return (diaryDAO.getDiary().map {
            DiaryItem(
                it.date,
                it.mood,
                it.doing,
                it.text,
                it.notification
            )
        } as? ArrayList<DiaryItem>) ?: arrayListOf()
    }

    suspend fun getDiaryItemByID(id: Long): DiaryItem {
        return diaryDAO.getDiaryItem(id).let {
            DiaryItem(
                it?.date,
                it?.mood,
                it?.doing,
                it?.text,
                it?.notification
            )
        }
    }

    suspend fun getDiaryItem(diaryItem: DiaryItem): DiaryItem {
        return diaryDAO.getDiaryItem(diaryItem.date).let {
            DiaryItem(
                it?.date,
                it?.mood,
                it?.doing,
                it?.text,
                it?.notification
            )
        }
    }

    suspend fun addDiary(diaryItem: DiaryItem) {
        diaryDAO.insertDiaryItem(
            DiaryItemEntity(
                diaryItem.date,
                diaryItem.mood,
                diaryItem.doing,
                diaryItem.text,
                diaryItem.notification
            )
        )
    }

    suspend fun delDiary(diaryItem: DiaryItem) {
        diaryDAO.delDiaryItem(
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