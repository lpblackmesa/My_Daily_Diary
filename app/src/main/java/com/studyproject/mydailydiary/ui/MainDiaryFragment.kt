package com.studyproject.mydailydiary.ui

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.AuthenticationStateEnum
import com.studyproject.mydailydiary.databinding.FragmentDrawerBinding
import com.studyproject.mydailydiary.databinding.HeaderDrawerBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import com.studyproject.mydailydiary.models.LoginViewModel
import com.studyproject.mydailydiary.repository.SharedPreferenceRepository
import com.studyproject.mydailydiary.ui.viewPagerAdapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainDiaryFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository
    private var binding: FragmentDrawerBinding? = null
    private val loginViewModel by viewModels<LoginViewModel>()
    private var headerBinding: HeaderDrawerBinding? = null
    private val diaryModel: EditDialogViewModel by activityViewModels()
    private var switch: SwitchCompat? = null
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


    //получение ответа от активити - запроса аутентификации
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            //получаем логи аутентификации
            val response = IdpResponse.fromResultIntent(result.data)
            if (result.resultCode == Activity.RESULT_OK) {
                // Successfully signed in user.
                Snackbar.make(
                    requireView(),
                    getString(R.string.login_successfull) +
                            " ${FirebaseAuth.getInstance().currentUser?.displayName}!",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                Snackbar.make(
                    requireView(), getString(R.string.login_unsuccessfull) +
                            " - ${response?.error?.errorCode}", Snackbar.LENGTH_LONG
                ).show()
            }
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsFragment -> findNavController().navigate(R.id.action_mainDiaryFragment_to_settingsFragment)
            R.id.login -> launchSignInFlow()
            R.id.logout -> AuthUI.getInstance().signOut(requireContext())
            R.id.setToFirebase -> diaryModel.diaryList.value?.let { diaryModel.setDiaryToFireBase(it) }
            R.id.getDataFromFirebase -> showGetFireBase()
        }
        binding?.drawer?.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //переводим флаг в состояние "не первый запуск"
        sharedPreferenceRepository.setIsFirstOpen()
        binding = FragmentDrawerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding?.actionBarFragment?.ViewPagerContainer
        val tab = binding?.actionBarFragment?.tab
        //binding header Drawer
        binding?.let {
            headerBinding = HeaderDrawerBinding.bind(it.navView.getHeaderView(0))
        }

        setupToolbar()
        observeAuthenticationState()

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
        headerBinding?.textView?.setOnLongClickListener {
            it.isVisible = !(it.isVisible)
            true
        }

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
        val toggle =
            ActionBarDrawerToggle(requireActivity(), binding?.drawer, R.string.open, R.string.close)
        binding?.drawer?.addDrawerListener(toggle)
        toggle.syncState()
    }

    //activity запрос аутентификации
    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(
            providers
        ).build()
        //создаем активити
        resultLauncher.launch(intent)
    }

    private fun showGetFireBase(){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.rewrite_warning))
            .setMessage(getString(R.string.firebase_rewrite_warning))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                //получаем данные из firebase
                diaryModel.getDiaryFromFireBase()
            }
            .setNegativeButton(getString(R.string.back)) { _, _ -> }
            .show()
    }

    //   обсервер состояния аутентификации
    private fun observeAuthenticationState() {
        loginViewModel.authenticationState.observe(
            viewLifecycleOwner,
            Observer { authenticationState ->
                when (authenticationState) {
                    AuthenticationStateEnum.AUTHENTICATED -> {
                        binding?.navView?.let {
                            it.menu.clear()
                            it.inflateMenu(R.menu.drawer_logged_menu)
                            switch = it.menu[2].actionView as? SwitchCompat
                            switch?.let { s ->
                                s.isChecked = true
                                diaryModel.setUseFirebase(true)
                                s.setOnCheckedChangeListener { _, b ->
                                    diaryModel.setUseFirebase(b)
                                }
                            }
                        }
                        binding?.drawer?.invalidate()
                        headerBinding?.textView?.text = FirebaseAuth.getInstance().currentUser?.uid
                        headerBinding?.userText?.text =
                            FirebaseAuth.getInstance().currentUser?.displayName
                        Picasso.get().load(FirebaseAuth.getInstance().currentUser?.photoUrl.toString())
                            .resize(200, 200)
                            .centerCrop()
                            .placeholder(R.drawable.menu_book)
                            .error(R.drawable.achievement)
                            .into(headerBinding?.imageView)
                    }
                    else -> {
                        diaryModel.setUseFirebase(false)
                        binding?.navView?.let {
                            it.menu.clear()
                            it.inflateMenu(R.menu.drawer_unlogged_menu)
                        }
                        binding?.drawer?.invalidate()
                        headerBinding?.let {
                            it.userText.text = getString(R.string.user)
                            it.imageView.setImageResource(R.drawable.menu_book_white)
                        }
                    }
                }
            }
        )
    }
}
