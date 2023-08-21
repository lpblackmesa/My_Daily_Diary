package com.studyproject.mydailydiary.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import com.studyproject.mydailydiary.ui.onboarding.OnboardingMainFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private val diaryModel: EditDialogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        Log.e("!", intent.action?:"null")
        if (intent.action == NOTIFICATION) {
            val id = intent.getLongExtra(EXTRA_NOTIFY, 0)
            Log.e("!", "id - $id")
            if (id != 0L) {
                diaryModel.getDiaryItemByID(id)
            }
        }
    }

    companion object {
        const val NOTIFICATION = "Notification"
        const val EXTRA_NOTIFY = "223"
        const val NOTIFICATION_ID = 223
        const val NOTIFICATION_NAME = "dailyDiary"
        const val NOTIFICATION_WORK = "appName_notification_work"
        const val CHANNEL_ID = "channel_ID"
        const val CHANNEL_NAME = "channel_NAME"

    }
}
