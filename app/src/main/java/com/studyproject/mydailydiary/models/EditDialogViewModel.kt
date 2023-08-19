package com.studyproject.mydailydiary.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditDialogViewModel : ViewModel() {

    //хранилище списка дневника
    val diaryList = MutableLiveData<ArrayList<DiaryItem>>()

    //обьект-репозиторий дневника
    private val diaryRep = DiaryRepository()

    //новый или измененный обьект из Диалога
    val diary_mesage: MutableLiveData<DiaryItem> by lazy {
        MutableLiveData<DiaryItem>()
    }
    private var useFirebase = false

    fun setUseFirebase(check: Boolean) {
        useFirebase = check
    }

    fun getUseFirebase(): Boolean = useFirebase

    fun getDiaryFromFireBase() {

        viewModelScope.launch(Dispatchers.IO) {
            diaryRep.getDiaryFromFireBase()
            getAllDiary()
        }
    }

    fun setDiaryToFireBase(diary: ArrayList<DiaryItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRep.setDiaryToFireBase(diary)
        }
    }

    fun setDiaryToFireBase(diaryItem: DiaryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRep.setDiaryToFireBase(diaryItem)
        }
    }

    fun delDiaryFromFireBase(diary: ArrayList<DiaryItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRep.delDiaryFromFireBase(diary)
        }
    }

    fun delDiaryFromFireBase(diaryItem: DiaryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRep.delDiaryFromFireBase(diaryItem)
        }
    }

    fun getAllDiary() = viewModelScope.launch(Dispatchers.IO) {
        //postvalue потоконебезопасно, для работы не в ui потоке
        //для UI потока используется value
        diaryList.postValue(diaryRep.getDiary())
    }

    fun delDiary(diaryItem: ArrayList<DiaryItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryItem.forEach {
                diaryRep.delDiary(it)
            }
            //обновляем view вызовом getNotes
            getAllDiary()
        }
    }

    fun addDiaryItem(item: DiaryItem) = viewModelScope.launch(Dispatchers.IO) {
        diaryRep.addDiary(item)
        //обновляем view вызовом getNotes
        getAllDiary()
    }

}