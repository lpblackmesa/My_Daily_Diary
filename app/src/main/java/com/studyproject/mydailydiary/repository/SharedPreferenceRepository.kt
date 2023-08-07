package com.studyproject.mydailydiary.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val SETTINGS_FILE = "settingsFile"
private const val USER_PROFILE_FILE = "userProfileFile"
private const val IS_FIRST_OPEN = "isFirstOpen"

object SharedPreferenceRepository {

    private var sharedPreferences: SharedPreferences? = null
    private var userPreferences: SharedPreferences? = null

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SETTINGS_FILE, Context.MODE_PRIVATE)
        userPreferences = context.getSharedPreferences(USER_PROFILE_FILE, Context.MODE_PRIVATE)
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