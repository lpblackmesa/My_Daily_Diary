package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.ActionBarBinding
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding

class ActionBarFragment: Fragment() {

    private lateinit var binding : ActionBarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ActionBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val appCompatActivity = activity as AppCompatActivity
//        setHasOptionsMenu(true)
//        appCompatActivity.actionBar?.setDisplayShowTitleEnabled(true);
//
//        binding?.actionBar?.inflateMenu(R.menu.toolbar_menu_main)
//        binding?.actionBar?.setTitle(R.string.app_name)
//        appCompatActivity.setSupportActionBar(binding?.actionBar)
//        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}