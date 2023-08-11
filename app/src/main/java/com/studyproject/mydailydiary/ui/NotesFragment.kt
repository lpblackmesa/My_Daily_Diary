package com.studyproject.mydailydiary.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.databinding.FragmentNotesBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private lateinit var binding: FragmentNotesBinding

    private val diaryModel : EditDialogViewModel by viewModels()

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
        binding.fabAddNote.setOnClickListener {
            initFAB()
            //вызываем диалог добавления заметки

            diaryModel.diary_mesage.value = null

            val editDiaryDialog = EditDiaryDialogFragment()
            editDiaryDialog.arguments =  bundleOf("MODE" to "CREATE_DIARY")
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            editDiaryDialog.show(transaction, "CREATE_DIARY")

        }
        binding.fabAddNotification.setOnClickListener{
            initFAB()
            diaryModel.string.value = "!!!!!!"
            diaryModel.diary_mesage.value = DiaryItem(121212,2,arrayListOf(1, 2, 4),"2",false)
            diaryModel.diaryItem = DiaryItem(121212,2,arrayListOf(1, 2, 4),"2",false)

            val editDiaryDialog = EditDiaryDialogFragment()
            editDiaryDialog.arguments =  bundleOf("MODE" to "CREATE_NOTIFY")
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            editDiaryDialog.show(transaction, "CREATE_NOTIFY")

            Handler(Looper.getMainLooper()).postDelayed({
                Log.e("!","delay")
                diaryModel.string.value = "!!!!!!"
                diaryModel.diary_mesage.value = DiaryItem(111111,10,arrayListOf(1, 2, 4),"delay",false) },3000) }
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