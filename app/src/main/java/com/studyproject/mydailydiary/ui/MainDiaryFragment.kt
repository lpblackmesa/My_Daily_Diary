package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.repository.SharedPreferenceRepository
import com.studyproject.mydailydiary.ui.viewPagerAdapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainDiaryFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener, ActionMode.Callback {


    private var binding: FragmentDrawerBinding? = null
    private var appBarConfiguration: AppBarConfiguration? = null
    private var actionMode: ActionMode? = null


    private fun setupToolbar() {
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding?.actionBarFragment?.actionBar)
        appCompatActivity.setTitle(R.string.app_name)
        setHasOptionsMenu(true)
       // binding?.actionBarFragment?.actionBar?.inflateMenu(R.menu.toolbar_menu_main)

        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

       // actionMode = appCompatActivity.startSupportActionMode(this)



        binding?.actionBarFragment?.actionBar?.setNavigationOnClickListener {
            if (binding?.drawer?.isDrawerOpen(GravityCompat.START) == true) {
                binding?.drawer?.closeDrawer(GravityCompat.START)
            } else {
                binding?.drawer?.openDrawer(GravityCompat.START)
            }
        }

        binding?.navView?.setNavigationItemSelectedListener(this)

        val toggle =
            ActionBarDrawerToggle(requireActivity(), binding?.drawer, R.string.open, R.string.close)

        binding?.drawer?.addDrawerListener(toggle)

        toggle.syncState()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsFragment -> findNavController().navigate(R.id.action_mainDiaryFragment_to_settingsFragment)
            R.id.item1 -> Toast.makeText(requireContext(), "Logout!", Toast.LENGTH_SHORT).show()

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

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.toolbar_select_menu, menu)
        Log.e("!","onCreateActionMode")
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = true

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        //TODO("Not yet implemented")
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
       // TODO("Not yet implemented")
    }

}
