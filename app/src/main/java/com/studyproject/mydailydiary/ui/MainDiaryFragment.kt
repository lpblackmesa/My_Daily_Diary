package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studyproject.mydailydiary.databinding.ActionBarBinding
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.databinding.FragmentMainTabayoutDiaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDiaryFragment:Fragment() {

    private lateinit var binding : FragmentDrawerBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDrawerBinding.inflate(inflater, container, false)
        return binding.root

    }
}