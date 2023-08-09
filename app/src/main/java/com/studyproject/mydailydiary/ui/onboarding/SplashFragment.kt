package com.studyproject.mydailydiary.ui.onboarding

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.repository.SharedPreferenceRepository


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       if (SharedPreferenceRepository.isFirstOpen()) {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(
                R.id.action_splashFragment_to_onboardingMainFragment) },1500) }

       else { findNavController().navigate(R.id.action_splashFragment_to_mainDiaryFragment) }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}