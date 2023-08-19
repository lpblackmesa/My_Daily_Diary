package com.studyproject.mydailydiary.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.entity.DiaryItemEntity
import com.studyproject.mydailydiary.db.DiaryDB
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DiaryRepository {


    suspend fun getDiaryFromFireBase(): ArrayList<DiaryItem> {
        val database = Firebase.database
        //создание пустого списка DiaryItem
        var arrayDiaryItem: ArrayList<DiaryItem> = arrayListOf()
        //если есть залогиненый пользователь
        FirebaseAuth.getInstance().currentUser?.let { user ->
            //заходим в папку пользователя
            val myRef = database.getReference(user.uid)
            //получение с ожиданием результата
            //для каждой подпапки добавляем ее содержимое в БД и в список
            myRef.get().await().children.forEach { snapshot ->
                snapshot.getValue<DiaryItem>()?.let { item ->
                    Log.i("!", "Convert value $item")
                    addDiary(item)
                    arrayDiaryItem.add(item)
                }
            }
        }
        Log.e("!", "return $arrayDiaryItem")
        return arrayDiaryItem
    }

    //запись массива данных в Firebase
    suspend fun setDiaryToFireBase(diary: ArrayList<DiaryItem>) {
        val database = Firebase.database
        FirebaseAuth.getInstance().currentUser?.let {
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
        val database = Firebase.database
        //если есть Юзер
        FirebaseAuth.getInstance().currentUser?.let { user ->
            //записываем в ветку Айди Юзер
            val myRef = database.getReference(user.uid)
            val myKey = diaryItem.date?.toString()
            myKey?.let { myRef.child(myKey).setValue(diaryItem) }
        }
    }

    //удаление одного item в Firebase
    suspend fun delDiaryFromFireBase(diaryItem: DiaryItem) {
        val database = Firebase.database
        //если есть Юзер
        FirebaseAuth.getInstance().currentUser?.let { user ->
            //записываем в ветку Айди Юзер
            val myRef = database.getReference(user.uid)
            val myKey = diaryItem.date?.toString()
            myKey?.let { myRef.child(myKey).removeValue() }
        }
    }

    suspend fun delDiaryFromFireBase(diary: ArrayList<DiaryItem>) {
        val database = Firebase.database
        FirebaseAuth.getInstance().currentUser?.let {
            val myRef = database.getReference(it.uid)

            val map = emptyMap<String, Any?>().toMutableMap()
            diary.forEach { item ->
                if (item.date != null) {
                    map[item.date.toString()] = null
                }
            }
            Log.e("!", map.toString())
            myRef.updateChildren(map as Map<String, Any?>)
        }
    }


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
        } ?: DiaryItem(0, 0, arrayListOf(), "", false)
    }

    suspend fun addDiary(diaryItem: DiaryItem) {

        Log.i("!", "addDiary value $diaryItem")
        DiaryDB.diaryDAO?.insertDiaryItem(
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