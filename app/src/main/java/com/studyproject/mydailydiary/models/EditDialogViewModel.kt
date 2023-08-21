package com.studyproject.mydailydiary.models


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.repository.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDialogViewModel @Inject constructor(private val diaryRep : DiaryRepository) : ViewModel() {

    //хранилище списка дневника
    val diaryList = MutableLiveData<ArrayList<DiaryItem>>()
    val diaryItem = MutableLiveData<DiaryItem>()

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

    fun getDiaryItemByID(id: Long){
        viewModelScope.launch(Dispatchers.IO){
            diaryItem.postValue(diaryRep.getDiaryItemByID(id))
        }
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