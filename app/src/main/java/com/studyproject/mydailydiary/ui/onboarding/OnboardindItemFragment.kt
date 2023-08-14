package com.studyproject.mydailydiary.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.ItemOnboardingBinding

class OnboardindItemFragment: Fragment() {
    private var binding: ItemOnboardingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemOnboardingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val step = arguments?.getInt("position") ?: 1
        when (step) {
            0 -> {
                binding?.imageText?.setText(R.string.onboarding_description_1)
                binding?.imageView?.setImageResource(R.drawable.menu_book)
            }

            1 -> {
                binding?.imageText?.setText(R.string.onboarding_description_2)
                binding?.imageView?.setImageResource(R.drawable.calendar_month_24)
            }

            2 -> {
                binding?.imageText?.setText(R.string.onboarding_description_3)
                binding?.imageView?.setImageResource(R.drawable.achievement)
            }
        }
    }
}