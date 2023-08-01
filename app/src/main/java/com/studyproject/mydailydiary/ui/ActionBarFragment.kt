package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
}