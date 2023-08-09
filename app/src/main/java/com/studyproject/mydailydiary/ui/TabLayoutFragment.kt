package com.studyproject.mydailydiary.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.databinding.FragmentMainTabayoutDiaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabLayoutFragment : Fragment() {

    private lateinit var binding: FragmentMainTabayoutDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainTabayoutDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}

