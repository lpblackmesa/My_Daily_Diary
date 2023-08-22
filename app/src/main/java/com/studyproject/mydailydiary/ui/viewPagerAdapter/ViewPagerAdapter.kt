package com.studyproject.mydailydiary.ui.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.studyproject.mydailydiary.ui.AchievementFragment
import com.studyproject.mydailydiary.ui.CalendarFragment
import com.studyproject.mydailydiary.ui.NotesFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    //переопределение количества элементов
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        //возвращение фрагментов в зависимости от позиции
        return when (position) {
            0 -> NotesFragment.newInstance()
            1 -> CalendarFragment.newInstance()
            2 -> AchievementFragment.newInstance()
            else -> CalendarFragment.newInstance()
        }
    }
}