package com.studyproject.mydailydiary.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val SETTINGS_FILE = "settingsFile"
private const val IS_FIRST_OPEN = "isFirstOpen"

@Singleton
class SharedPreferenceRepository @Inject constructor(@ApplicationContext context: Context) {

    private var sharedPreferences: SharedPreferences? = null

     init{
        sharedPreferences = context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE)
    }

    fun setIsFirstOpen() {
        sharedPreferences?.edit {
            putBoolean(IS_FIRST_OPEN, false)
        }
    }

    fun isFirstOpen(): Boolean {
        return sharedPreferences?.getBoolean(IS_FIRST_OPEN, true) ?: true
    }

}