package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.repository.SharedPreferenceRepository
import com.studyproject.mydailydiary.ui.viewPagerAdapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDiaryFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {


    private var binding: FragmentDrawerBinding? = null

    private fun setupToolbar() {
        //привязываем AppBar к активити и устанавливаем заголовок
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding?.actionBarFragment?.actionBar)
        appCompatActivity.setTitle(R.string.app_name)
        setHasOptionsMenu(true)
        //устанавливаем кнопку навигации drawer и определяем его поведение на нажатие
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.actionBarFragment?.actionBar?.setNavigationOnClickListener {
            if (binding?.drawer?.isDrawerOpen(GravityCompat.START) == true) {
                binding?.drawer?.closeDrawer(GravityCompat.START)
            } else {
                binding?.drawer?.openDrawer(GravityCompat.START)
            }
        }
        //привязываем drawer к ActionBar , Drawer и  NavigationView
        binding?.navView?.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(requireActivity(), binding?.drawer, R.string.open, R.string.close)
        binding?.drawer?.addDrawerListener(toggle)
        toggle.syncState()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsFragment -> findNavController().navigate(R.id.action_mainDiaryFragment_to_settingsFragment)
            R.id.item1 -> {
                binding?.actionBarFragment?.actionBar?.inflateMenu(R.menu.toolbar_menu_main)
                invalidateOptionsMenu(requireActivity())
            }

        }
        binding?.drawer?.closeDrawer(GravityCompat.START)
        return true
    }

    //список иконок и массив строк, которые приходится восстанавливать в TabConfigurationStrategy
    private val tabIconList = listOf(
        R.drawable.menu_book,
        R.drawable.calendar_month_24,
        R.drawable.achievement
    )
    private val tabTextList = listOf(
        R.string.notes,
        R.string.calendar,
        R.string.achievements
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //переводим флаг в состояние "не первый запуск"
        SharedPreferenceRepository.setIsFirstOpen()
        // Inflate the layout for this fragment
        binding = FragmentDrawerBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding?.actionBarFragment?.ViewPagerContainer
        val tab = binding?.actionBarFragment?.tab


        setupToolbar()

        //меняем поведение кнопки назад
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding?.drawer?.isDrawerOpen(GravityCompat.START) == true) {
                        binding?.drawer?.closeDrawer(GravityCompat.START)
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })


        //устанавливаем адаптер в ViewPager2
        val adapter = ViewPagerAdapter(this)
        viewPager?.adapter = adapter
        //связываем ViewPager и TabLayout
        if (tab != null && viewPager != null) {
            TabLayoutMediator(tab, viewPager) { tab, pos ->
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

}
