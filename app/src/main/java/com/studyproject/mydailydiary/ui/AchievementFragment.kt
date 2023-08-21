package com.studyproject.mydailydiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.studyproject.mydailydiary.databinding.FragmentAchievementBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AchievementFragment : Fragment() {
    private  var binding: FragmentAchievementBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementBinding.inflate(inflater, container, false)
        return binding?.root
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            AchievementFragment()
    }
}