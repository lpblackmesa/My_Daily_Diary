package com.studyproject.mydailydiary.ui.onboarding

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    //переопределение количества элементов
    override fun getItemCount(): Int = 3

    //создание фрагментов онбординга с агрументами позиции
    override fun createFragment(position: Int): Fragment =
        OnboardindItemFragment().apply {
            arguments = bundleOf("position" to position)
        }
}