package com.studyproject.mydailydiary.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    //переопределение количества элементов
    override fun getItemCount(): Int  = 3

    override fun createFragment(position: Int): Fragment {

        //возвращение фрагментов в зависимости от позиции
         return  when (position) {

//             0 -> NotesFragment()
//             1 -> CalendarFragment()
//             2 -> AchievementFragment()
//             else -> CalendarFragment()



             0 -> NotesFragment.newInstance()
             1 -> CalendarFragment.newInstance()
             2 -> AchievementFragment.newInstance()
             else -> CalendarFragment.newInstance()


         }
    //list[position]
        }
}