package com.studyproject.mydailydiary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//аннотация Hilt для Application
//необходимо добавить этот класс в AndriodManifest, чтобы этот класс работал в проекте
@HiltAndroidApp
class DiaryApp: Application() {
}