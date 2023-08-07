package com.studyproject.mydailydiary

import android.app.Application
import com.studyproject.mydailydiary.db.DiaryDB
import com.studyproject.mydailydiary.repository.SharedPreferenceRepository
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class DiaryApp: Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferenceRepository.init(applicationContext)
        DiaryDB.initDB(applicationContext)
    }
}