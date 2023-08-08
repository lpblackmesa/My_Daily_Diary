package com.studyproject.mydailydiary.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.ActionBarBinding
import com.studyproject.mydailydiary.ui.onboarding.OnboardingMainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}