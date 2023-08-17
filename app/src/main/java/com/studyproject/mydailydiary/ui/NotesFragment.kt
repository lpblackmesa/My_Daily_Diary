package com.studyproject.mydailydiary.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.HolderType
import com.studyproject.mydailydiary.data.Keys
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity
import com.studyproject.mydailydiary.databinding.FragmentNotesBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import com.studyproject.mydailydiary.ui.recycleAdapter.CustomSelectionPredicate
import com.studyproject.mydailydiary.ui.recycleAdapter.HolderItemClickListener
import com.studyproject.mydailydiary.ui.recycleAdapter.ItemDiaryAdapter
import com.studyproject.mydailydiary.ui.recycleAdapter.ItemLookup
import com.studyproject.mydailydiary.ui.recycleAdapter.ItemsKeyProvider
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class NotesFragment : Fragment(), HolderItemClickListener {

    private var binding: FragmentNotesBinding? = null
    private var tracker: SelectionTracker<Long>? = null
    private val diaryModel: EditDialogViewModel by activityViewModels()

    fun showDeleteDialog(itemsCount: MutableList<RecycleViewEntity>) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_note_dialog))
            .setMessage(getString(R.string.delete_question) + " ${itemsCount.size} " + getString(R.string.elements))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                //удаляем каждый элемент из списка
                itemsCount.forEach {
                    if (it.data != null) {
                        diaryModel.delDiary(it.data)
                    }
                }
            }

            .setNegativeButton(getString(R.string.back)) { _, _ ->
            }
            .show()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        tracker?.let {
            when (it.selection.size()) {
                0 -> inflater.inflate(R.menu.toolbar_menu_main, menu)
                1 -> inflater.inflate(R.menu.toolbar_select_one_menu, menu)
                else -> inflater.inflate(R.menu.toolbar_select_several_menu, menu)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete -> {
            tracker?.let {

                val mainAdapter = binding?.recyclerView?.adapter as ItemDiaryAdapter
                //делаем список выделенных элементов recycleEntity
                val selected = mainAdapter.currentList.filter { item ->
                    it.selection.contains(item.id)
                }.toMutableList()
                // вызываем диалог с удалением и передаем ему список
                showDeleteDialog(selected)

                it.clearSelection()
            }
            ActivityCompat.invalidateOptionsMenu(requireActivity())
            true
        }

        R.id.action_edit -> {
            tracker?.let {

                val mainAdapter = binding?.recyclerView?.adapter as ItemDiaryAdapter
                val selected = mainAdapter.currentList.firstOrNull { item ->
                    item.id == it.selection.first()
                }
                if (selected != null) {
                    showDialogFragment(Keys.EDIT, selected.data)
                }
                it.clearSelection()
            }
            ActivityCompat.invalidateOptionsMenu(requireActivity())
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


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

        setHasOptionsMenu(true)


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
            showDialogFragment(Keys.CREATE, null)

        }
        binding?.fabAddNotification?.setOnClickListener {
            initFAB()
            //запуск ДиалогФрагмента

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
        var id: Long = 0
        map.forEach() { entry ->
//добавляем в список заголовок с датой
            newList.add(
                RecycleViewEntity(++id, HolderType.HEADER, null, entry.value[0].date)
            )
            //LessonEntity(type = TrainingType.HEADER, null, entry.key.))
            // преобразовывем ключ мапы в список RecycleViewEntity
            entry.value.mapTo(newList) {
                if (it.notification) {
                    RecycleViewEntity(++id, HolderType.NOTIFY, it, null)
                } else {
                    RecycleViewEntity(++id, HolderType.DIARY, it, null)
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

                //????
                setHasFixedSize(true)

                val mainAdapter = ItemDiaryAdapter(this@NotesFragment)
                //создаем tracker для работы с выделением item в recyclerView
                binding?.let {
                    adapter = mainAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    tracker = SelectionTracker.Builder(
                        "selectionItem",
                        it.recyclerView,
                        ItemsKeyProvider(mainAdapter),
                        ItemLookup(it.recyclerView),
                        StorageStrategy.createLongStorage()
                    ).withSelectionPredicate(
                        //правило выделений
                        CustomSelectionPredicate(mainAdapter)
                    ).build().apply {
                        addObserver(
                            object : SelectionTracker.SelectionObserver<Long>() {
                                override fun onSelectionChanged() {
                                    //при каждом изменении количества выбранных элементов
                                    // вызываем пересоздание меню AppBar и записываем в заголовок количество элементов
                                    val appCompatActivity = activity as AppCompatActivity
                                    ActivityCompat.invalidateOptionsMenu(appCompatActivity)
                                    val count = tracker?.selection?.size()
                                    count?.let {
                                        if (count > 0) {
                                            appCompatActivity.title =
                                                getString(R.string.selected) + " ${tracker?.selection?.size()}"
                                        } else {
                                            appCompatActivity.setTitle(R.string.app_name)
                                        }
                                    }
                                }
                            })
                    }
                    //передаем в адаптер tracker
                    mainAdapter.setMyTracker(tracker)

                }
            }
            (adapter as? ItemDiaryAdapter)?.submitList(list)
            //перерисовка recyclerView
            adapter?.notifyDataSetChanged()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
    }

    private fun showDialogFragment(type: Keys, item: DiaryItem?) {

        val editDiaryDialog = EditDiaryDialogFragment()
        editDiaryDialog.arguments = bundleOf(
            type.KEY to type.VALUE,
            Keys.ITEM.KEY to item
        )
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        editDiaryDialog.show(transaction, type.VALUE)

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

    //обрабатываем клики на recyclerView item
    override fun onHolderItemClick(item: RecycleViewEntity) {
        when (item.type) {
            HolderType.DIARY -> showDialogFragment(Keys.SHOW, item.data)
            // HolderType.NOTIFY ->
            else -> { //throw IllegalArgumentException("Invalid view type in RecycleViewEntity")
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NotesFragment()
    }

}