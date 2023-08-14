package com.studyproject.mydailydiary.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditDialogViewModel : ViewModel() {

    //хранилище списка дневника
    val diaryList = MutableLiveData<ArrayList<DiaryItem>>()
    val diaryItem = MutableLiveData<DiaryItem>()
    //обьект-репозиторий дневника
    val diaryRep = DiaryRepository()
    //новый или извененный обьект из Диалога
    val diary_mesage : MutableLiveData<DiaryItem> by lazy {
            MutableLiveData<DiaryItem>()
    }
    fun getAllDiary() = viewModelScope.launch(Dispatchers.IO) {
        //postvalue потоконебезопасно, для работы не в ui потоке
        //для UI потока используется value
        diaryList.postValue(diaryRep.getDiary())
    }
    fun delDiary (diaryItem: DiaryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            diaryRep.delDiary(diaryItem)
            //обновляем view вызовом getNotes
            getAllDiary()
        }
    }

    fun addDiaryItem(item: DiaryItem)= viewModelScope.launch(Dispatchers.IO){
        diaryRep.addDiary(item)
        //обновляем view вызовом getNotes
        getAllDiary()
    }

    fun getDiaryItem (item: DiaryItem)= viewModelScope.launch(Dispatchers.IO) {
        diaryItem.postValue(diaryRep.getDiaryItem(item))
    }
}