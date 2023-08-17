package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.ui.onboarding.OnboardingMainFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override  fun onCreateOptionsMenu(menu: Menu): Boolean {
      //  menuInflater.inflate(R.menu.toolbar_menu_main, menu)
        return true
    }
}
