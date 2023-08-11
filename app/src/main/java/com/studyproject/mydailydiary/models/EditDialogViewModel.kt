package com.studyproject.mydailydiary.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studyproject.mydailydiary.data.DiaryItem

class EditDialogViewModel : ViewModel() {

    val string = MutableLiveData<String>()

        val diary_mesage : MutableLiveData<DiaryItem> by lazy {
            MutableLiveData<DiaryItem>()
    }
    var diaryItem : DiaryItem? = null



}