package com.studyproject.mydailydiary.ui

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.os.BundleCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.databinding.DialogfragmentEditBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import com.studyproject.mydailydiary.data.Doing
import com.studyproject.mydailydiary.data.Keys
import com.studyproject.mydailydiary.data.Mood
import com.studyproject.mydailydiary.ui.spinnerAdapter.SpinnerCustomAdapter
import java.text.SimpleDateFormat
import java.util.Calendar

//наследуемся от DialogFragment
class EditDiaryDialogFragment : DialogFragment() {

    private var binding: DialogfragmentEditBinding? = null
    private var date = Calendar.getInstance().timeInMillis

    //список всех чипов
    private val chipList = ArrayList<Chip>()

    private val diaryModel: EditDialogViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //inflate диалогового фрагмента
        binding = DialogfragmentEditBinding.inflate(LayoutInflater.from(context))

        return activity?.let { _ ->
            //подключаем кастомный Spinner адаптер к Spinner с двумя разными layout
            val adapter = SpinnerCustomAdapter(requireContext(), Mood.values())
            adapter.setDropDownViewResource(R.layout.item_spinner_dropped)
            binding?.spinner.let {
                it?.adapter = adapter
                it?.setSelection(0, false)
                it?.prompt = getString(R.string.mood_choose)
            }
            //устанавливаем текущее время
            binding?.dateText?.text = SimpleDateFormat("EEEE , dd MMMM yyyy  HH:mm").format(date)


//спиннер листенер
            binding?.spinner?.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

            //создаем программно chip, сколько есть ENUM занятий
            enumValues<Doing>().forEachIndexed { index, it ->
                var chip = Chip(requireContext())
                //первые 8 chip помещаем в первый chipGroup, остальные во второй
                if (index < 9) {
                    chip = getLayoutInflater().inflate(
                        R.layout.chip_choise,
                        binding?.chipGroup,
                        false
                    ) as Chip
                    binding?.chipGroup?.addView(chip)
                } else {
                    chip = getLayoutInflater().inflate(
                        R.layout.chip_choise,
                        binding?.chipGroup2,
                        false
                    ) as Chip
                    binding?.chipGroup2?.addView(chip)
                }
                chip.id = index
                chip.setText(it.description)
                chip.isCheckable = true
                chip.setChipIconResource(it.iconRes)
                chip.setChipIconVisible(true)
                //добавляем чип в список чипов
                chipList.add(chip)
            }

            //создание диалога

            val dialog: AlertDialog = AlertDialog.Builder(requireContext())
                .setView(binding?.root)
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.back) { dialog, id -> dialog.cancel() }
                .setNeutralButton(R.string.edit, null)
                .show()

            val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val editButton: Button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)

            //Получаем АГРУМЕНТЫ для получения типа диалога
            val showMode = arguments?.getString(Keys.CREATE.KEY) ?: Keys.CREATE.VALUE
            var diaryItem: DiaryItem? = null
            //получаем обьект для отображения
            // получаем данные и заполняем фрагмент

            if (Build.VERSION.SDK_INT >= 33) { // TIRAMISU
                diaryItem = arguments?.getParcelable("ITEM", DiaryItem::class.java)
            } else {
                diaryItem = BundleCompat.getParcelable(
                    this.requireArguments(),
                    "ITEM",
                    DiaryItem::class.java
                )
            }

            //переопределение нажатий на кнопки
            positiveButton.setOnClickListener {
                binding?.let {
                    diaryModel.diary_mesage.value = DiaryItem(
                        date,
                        it.spinner.selectedItemPosition,
                        getSelectedChip(),
                        it.messageEdit.text.toString(),
                        false
                    )
                }
                dialog.cancel()
            }

            editButton.setOnClickListener {
                editButton.isVisible = false
                positiveButton.isVisible = true
                positiveButton.setText(R.string.save)
                if (diaryItem != null) {
                    setDataToFragment(diaryItem, true)
                }
            }
            editButton.isVisible = false
            if (diaryItem != null) {
                //заполнение фрагмента данными, если они есть
                when (showMode) {
                    Keys.SHOW.VALUE -> {
                        date = diaryItem.date
                        binding?.messageEdit?.hint = ""
                        binding?.messageLayout?.hint = ""
                        editButton.isVisible = true
                        positiveButton.isVisible = false
                        setDataToFragment(diaryItem, false)
                    }

                    Keys.EDIT.VALUE -> {
                        date = diaryItem.date
                        editButton.isVisible = false
                        setDataToFragment(diaryItem, true)
                    }

                }
            }


            binding?.let {
                it.messageEdit.addTextChangedListener { edit ->
                    //если текст пустой - блокируем кнопку добавить
                    positiveButton.isEnabled = edit?.isBlank() == false
                }
            }


            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    private fun setDataToFragment(item: DiaryItem, enabled: Boolean) {

        //заполнение фрагмента данными
        binding?.let {
            it.dateText.text = SimpleDateFormat("EEEE , dd MMMM yyyy  HH:mm").format(item.date)
            it.spinner.setSelection(item.mood)
            it.spinner.isEnabled = enabled
            it.messageEdit.setText(item.text)
            it.messageEdit.isEnabled = enabled
            it.chipGroup2.isClickable = enabled
            it.chipGroup.isClickable = enabled
        }

        chipList.forEach {
            it.isClickable = enabled
        }
        //выделяем выбранные занятия
        item.doing.forEachIndexed { index, it ->
            chipList[index].isChecked = true
        }
    }

    private fun getSelectedChip(): ArrayList<Int> {
        val list = arrayListOf<Int>()
        chipList.forEachIndexed() { index, it ->
            if (it.isChecked) {
                list.add(index)
            }
        }
        return list
    }
}


////получение всех отмеченных занятий
//
//        binding?.messageEdit?.append("\n")
//
//    }
//}