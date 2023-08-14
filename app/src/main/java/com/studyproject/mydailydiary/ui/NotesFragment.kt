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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.HolderType
import com.studyproject.mydailydiary.data.Keys
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity
import com.studyproject.mydailydiary.databinding.FragmentNotesBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class NotesFragment : Fragment(), HolderItemClickListener {

    private var binding: FragmentNotesBinding? = null

    private val diaryModel: EditDialogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //запрашиваем значения списка из репозитория через viewmodel и обновляем
        diaryModel.getAllDiary()


        diaryModel.addDiaryItem(DiaryItem(323454234543,5, arrayListOf(),"noty",true))
        diaryModel.addDiaryItem(DiaryItem(323454234546,7, arrayListOf(2,6,8),"text",false))
        diaryModel.addDiaryItem(DiaryItem(323454234547,10, arrayListOf(1,11,0),"text2",false))

        //подписываемся на обновления message , если есть изменения, добавляем
        diaryModel.diary_mesage.observe(viewLifecycleOwner) {
            if (it != null) {
                diaryModel.addDiaryItem(it)
            }
        }
        //подписываемся на обновления списка из базы данных
        diaryModel.diaryList.observe(viewLifecycleOwner) {

            updateRecycler(getSortedRecyclerItems(it))

        }


        //устанавливаем плавающую кнопку в начальное положение
        binding?.fabAdd?.shrink()
        // обрабатываем нажатия на плавающие кнопки
        binding?.fabAdd?.setOnClickListener {
            //убираем или показываем доп кнопки
            initFAB()
        }
        binding?.fabAddNote?.setOnClickListener {
            initFAB()
            //вызываем диалог добавления заметки

            val editDiaryDialog = EditDiaryDialogFragment()
            editDiaryDialog.arguments = bundleOf(
                Keys.EDIT.KEY to Keys.EDIT.VALUE,
                Keys.ITEM.KEY to DiaryItem(
                    121272143542,
                    8,
                    arrayListOf(0, 1, 2, 4, 11),
                    "text!222",
                    false
                )
            )
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            editDiaryDialog.show(transaction, Keys.EDIT.VALUE)

//            val editDiaryDialog = EditDiaryDialogFragment()
//            editDiaryDialog.arguments = bundleOf(Keys.CREATE.KEY to Keys.CREATE.VALUE)
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            editDiaryDialog.show(transaction, Keys.CREATE.VALUE)


        }
        binding?.fabAddNotification?.setOnClickListener {
            initFAB()
            //запуск ДиалогФрагмента
            val editDiaryDialog = EditDiaryDialogFragment()
            editDiaryDialog.arguments = bundleOf(
                Keys.SHOW.KEY to Keys.SHOW.VALUE,
                Keys.ITEM.KEY to DiaryItem(1212143542, 2, arrayListOf(0, 1, 2, 4), "text!", false)
            )
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            editDiaryDialog.show(transaction, Keys.SHOW.VALUE)


//            val editDiaryDialog = EditDiaryDialogFragment()
//            editDiaryDialog.arguments = bundleOf(Keys.CREATE_NOTIFY.KEY to Keys.CREATE_NOTIFY.VALUE, Keys.ITEM.KEY to DiaryItem(1212143542, 2, arrayListOf(0, 1, 2, 4), "text!", false))
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            editDiaryDialog.show(transaction, Keys.CREATE_NOTIFY.VALUE)

        }
    }

    private fun getSortedRecyclerItems(baseItem: ArrayList<DiaryItem>): ArrayList<RecycleViewEntity> {
        //создаем список с сортированным ссписком DiaryItem
        val list = baseItem.sortedByDescending {
            it.date
        }
//создаем мапу с ключом - дата, значение - список элементов с этой датой
        val map = emptyMap<String, ArrayList<DiaryItem>>().toMutableMap()
        list.forEach {
            //ключ мапы - дата "dd MMMM yyyy". Если такая уже есть, добавляем туда

            val dateStr = SimpleDateFormat("dd MMMM yyyy").format(it.date)
            if (map[dateStr] == null) {
                map[dateStr] = ArrayList()
            }
            map[dateStr]?.add(it)
        }
        //создаем список RecycleViewEntity
        val newList = ArrayList<RecycleViewEntity>()
        map.forEach() { entry ->
//добавляем в список заголовок с датой
            newList.add(
                RecycleViewEntity(HolderType.HEADER, null, entry.value[0].date)
            )
            //LessonEntity(type = TrainingType.HEADER, null, entry.key.))
            // преобразовывем ключ мапы в список RecycleViewEntity
            entry.value.mapTo(newList) {
                if (it.notification) {
                    RecycleViewEntity(HolderType.NOTIFY, it, null)
                } else {
                    RecycleViewEntity(HolderType.DIARY, it, null)
                }
            }
        }
        return newList
    }


    //функция обновления recycler
    private fun updateRecycler(list: ArrayList<RecycleViewEntity>) {
        //инициализация recyclerView
        binding?.recyclerView?.run {
            if (adapter == null) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ItemDiaryAdapter(this@NotesFragment)
            }
            (adapter as? ItemDiaryAdapter)?.submitList(list)
            //перерисовка recyclerView
            adapter?.notifyDataSetChanged()
        }
    }


    // функция управления поведением кнопок при нажатии
    private fun initFAB() {
        binding?.let {
            if (it.fabAdd.isExtended) {
                with(it) {
                    fabAdd.shrink()
                    fabAddNote.hide()
                    fabAddNoteText.visibility = View.GONE
                    fabAddNotification.hide()
                    fabAddNotificationText.visibility = View.GONE
                }
            } else {
                with(it) {
                    fabAdd.extend()
                    fabAddNote.show()
                    fabAddNoteText.visibility = View.VISIBLE
                    fabAddNotification.show()
                    fabAddNotificationText.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCHolderItemlick(item: DiaryItem) {
        TODO("Not yet implemented")
    }

    override fun onHolderItemLongClick(item: DiaryItem) {
        TODO("Not yet implemented")
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NotesFragment()
    }

}