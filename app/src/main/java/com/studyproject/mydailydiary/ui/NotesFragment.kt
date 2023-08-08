package com.studyproject.mydailydiary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.studyproject.mydailydiary.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    //устанавливаем плавающую кнопку в начальное положение
        binding.fabAdd.shrink()
        // обрабатываем нажатия на плавающие кнопки
        binding.fabAdd.setOnClickListener{
            //убираем или показываем доп кнопки
            initFAB()
        }
        binding.fabAddNote.setOnClickListener{
            initFAB()
            showToast("Hello From Add Note Button")
        }
        binding.fabAddNotification.setOnClickListener{
            initFAB()
            showToast("Hello From Add Noti Button")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    // функция управления поведением кнопок при нажатии
    private fun initFAB() {
        if (binding.fabAdd.isExtended) {
            binding.fabAdd.shrink()
            binding.fabAddNote.hide()
            binding.fabAddNoteText.visibility = View.GONE
            binding.fabAddNotification.hide()
            binding.fabAddNotificationText.visibility = View.GONE
        } else {
            binding.fabAdd.extend()
            binding.fabAddNote.show()
            binding.fabAddNoteText.visibility = View.VISIBLE
            binding.fabAddNotification.show()
            binding.fabAddNotificationText.visibility = View.VISIBLE
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            NotesFragment()
    }
}