package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.models.EditDialogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private val diaryModel: EditDialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //отслеживаем интенты
    //если это от нотификации - берем id , и получаем из БД элемент
    //viewModel получит результат, в NotesFragment - observer
    override fun onResume() {
        super.onResume()
        if (intent?.action == NOTIFICATION) {
            val id = intent.getLongExtra(EXTRA_NOTIFY, 0)
            Log.e("!", "id - $id")
            if (id != 0L) {
                diaryModel.getDiaryItemByID(id)
            }
            intent = null
        }
    }

    companion object {
        const val NOTIFICATION = "Notification"
        const val EXTRA_NOTIFY = "223"
        const val NOTIFICATION_TEXT = "noty_text"
        const val CHANNEL_ID = "channel_ID"
        const val CHANNEL_NAME = "channel_NAME"
    }
}
