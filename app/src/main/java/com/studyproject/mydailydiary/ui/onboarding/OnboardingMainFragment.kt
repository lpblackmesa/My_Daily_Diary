package com.studyproject.mydailydiary.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.FragmentOnboardingMainBinding

class OnboardingMainFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingMainBinding

    //переменная callback - переопределяем метод в ViewPager2 для замены кнопок
    private var viewPager2Callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            when (position) {
                0 -> {
                    binding.backText.isVisible = false
                    //устанавливаем кнопке переход на след страницу и текст
                    binding.nextText.apply {
                        setText(R.string.next)
                        setOnClickListener {
                            binding.pager.currentItem += 1
                        }
                    }
                }
                1 -> {
                    binding.backText.isVisible = true
                    binding.backText.apply {
                        setText(R.string.back)
                        setOnClickListener {
                            binding.pager.currentItem -= 1
                        }
                    }
                    binding.nextText.apply {
                        setText(R.string.next)
                        setOnClickListener {
                            binding.pager.currentItem += 1
                        }
                    }
                }

                2 -> {
                    binding.nextText.apply {
                        setText(R.string.finish)
                        setOnClickListener {
                            findNavController().navigate(R.id.action_onboardingMainFragment_to_mainDiaryFragment)
                        }
                    }
                }
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //подключаем прокрутке адаптер и передаем ему фрагмент
        binding.pager.adapter = OnboardingAdapter(this)

        //переопределяем метод в ViewPager2 для замены кнопок
        binding.pager.registerOnPageChangeCallback(viewPager2Callback)

        //передаем circleIndicator - ViewPager2
        binding.circleIndicator.attachTo(binding.pager)
    }

    override fun onDestroy() {
        //отписываем Callback при удалении фрагмента, чтобы избежать утечки памяти
        binding.pager.unregisterOnPageChangeCallback(viewPager2Callback)
        super.onDestroy()
    }
}