package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.ActionBarBinding
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.databinding.FragmentMainTabayoutDiaryBinding
import com.studyproject.mydailydiary.repository.SharedPreferenceRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDiaryFragment:Fragment() {

    private lateinit var binding : FragmentDrawerBinding

    //список иконок и массив строк, которые приходится восстанавливать в TabConfigurationStrategy
    private val tabIconList = listOf(
        R.drawable.menu_book,
        R.drawable.calendar_month_24,
        R.drawable.achievement)
    private val tabTextList  = listOf(
        R.string.notes,
        R.string.calendar,
        R.string.achievements)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //переводим флаг в состояние "не первый запуск"
        SharedPreferenceRepository.setIsFirstOpen()
        // Inflate the layout for this fragment
        binding = FragmentDrawerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.actionBarFragment.TabLayoutFragment.ViewPagerContainer
        val tab = binding.actionBarFragment.TabLayoutFragment.tab

        //устанавливаем адаптер в ViewPager2
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
        //связываем ViewPager и TabLayout
        TabLayoutMediator(tab, viewPager)
        { tab, pos ->
            //устанавливаем иконки и текст в TabLayout
            tab.setIcon(tabIconList[pos])
            tab.setText(tabTextList[pos])
            // установка иконки-бэйджа при необходимости
            //Удалить бейджи можно методом tab.removeBadge()
            if (pos == 2) {
                val badge = tab.getOrCreateBadge()
                badge.number = 1
            }
        }.attach()



    }
}
