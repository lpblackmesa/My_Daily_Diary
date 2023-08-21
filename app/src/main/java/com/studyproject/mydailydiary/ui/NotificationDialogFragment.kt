package com.studyproject.mydailydiary.ui

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.os.BundleCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.DiaryItem
import com.studyproject.mydailydiary.data.Keys
import com.studyproject.mydailydiary.databinding.DialogfragmentNotificationBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class NotificationDialogFragment : DialogFragment() {

    private var binding: DialogfragmentNotificationBinding? = null
    private val diaryModel: EditDialogViewModel by activityViewModels()
    private var date: Calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogfragmentNotificationBinding.inflate(layoutInflater)
        return activity?.let {
            val dialog: AlertDialog = AlertDialog.Builder(requireContext())
                .setView(binding?.root)
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.back) { dialog, id -> dialog.cancel() }
                .setNeutralButton(R.string.edit, null)
                .show()

            val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val editButton: Button = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)

            //Получаем АГРУМЕНТЫ для получения типа диалога
            val showMode = arguments?.getString(Keys.CREATE_NOTIFY.KEY) ?: Keys.CREATE_NOTIFY.VALUE
            var diaryItem: DiaryItem? = null
            //получаем обьект для отображения
            // получаем данные и заполняем фрагмент

            if (Build.VERSION.SDK_INT >= 33) { // TIRAMISU
                diaryItem = arguments?.getParcelable(Keys.ITEM.KEY, DiaryItem::class.java)
            } else {
                diaryItem = BundleCompat.getParcelable(
                    this.requireArguments(),
                    Keys.ITEM.KEY,
                    DiaryItem::class.java
                )
            }

            //переопределение нажатий на кнопки
            positiveButton.setOnClickListener {
                binding?.let {
                    var mood = 0
                    if (binding?.repeatCheckBox?.isChecked == true) {
                        mood = 1
                    }
                    //записываем в базу данных
                    val newItem = DiaryItem(
                        date.timeInMillis,
                        mood,
                        arrayListOf(),
                        it.messageEdit.text.toString(),
                        true
                    )
                    diaryModel.diary_mesage.value = newItem
                    //если дата была изменена, удаляем оригинал из базы
                    if ((newItem.date != diaryItem?.date) && (diaryItem != null))
                        diaryModel.delDiary(diaryItem)
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
                    Keys.SHOW_NOTIFY.VALUE -> {
                        binding?.messageEdit?.hint = ""
                        binding?.messageLayout?.hint = ""
                        editButton.isVisible = true
                        positiveButton.isVisible = false
                        setDataToFragment(diaryItem, false)
                    }
                    Keys.EDIT_NOTIFY.VALUE -> {
                        editButton.isVisible = false
                        setDataToFragment(diaryItem, true)
                    }
                }
            }

            //устанавливаем текущее время
            binding?.date?.text = SimpleDateFormat("dd MMMM yyyy").format(date.timeInMillis)
            binding?.textClock?.text = SimpleDateFormat("HH:mm").format(date.timeInMillis)

            binding?.dateText?.setOnLongClickListener {
                binding?.dateText?.text = (diaryItem?.date ?: 0).toString()
                true
            }
            //если текст пустой - блокируем кнопку добавить
            if (binding?.messageEdit?.text?.isBlank() == true) {
                positiveButton.isEnabled = false
            }

            binding?.let {
                it.messageEdit.addTextChangedListener { edit ->
                    positiveButton.isEnabled = edit?.isBlank() == false
                }
            }
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(date.timeInMillis)
                .build()

            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(date.get(Calendar.HOUR_OF_DAY))
                .setMinute(date.get(Calendar.MINUTE))
                .setTitleText(getString(R.string.select_time))
                .build()

            datePicker.addOnPositiveButtonClickListener {
                val newDate = Calendar.getInstance()
                newDate.timeInMillis = it
                date.set(
                    newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH),
                    newDate.get(Calendar.DAY_OF_MONTH)
                )
                binding?.date?.text = SimpleDateFormat("dd MMMM yyyy").format(date.timeInMillis)
            }
            timePicker.addOnPositiveButtonClickListener {

                date.set(Calendar.HOUR_OF_DAY, timePicker.hour)
                date.set(Calendar.MINUTE, timePicker.minute)
                binding?.textClock?.text = SimpleDateFormat("HH:mm").format(date.timeInMillis)
            }

            binding?.editDateButton?.setOnClickListener {
                datePicker.show(parentFragmentManager, "Data picker")
            }
            binding?.editTimeButton?.setOnClickListener {
                timePicker.show(parentFragmentManager, "Time picker")
            }

            dialog
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setDataToFragment(item: DiaryItem, enabled: Boolean) {
        //заполнение фрагмента данными

        binding?.let {
            date.timeInMillis = item.date ?: 0
            //если повторять каждый год, отмечаем
            if ((item.mood ?: 0) > 0) {
                it.repeatCheckBox.isChecked = true
            }
            date.timeInMillis = item.date ?: 0
            it.messageEdit.setText(item.text)
            it.messageEdit.isEnabled = enabled
            it.editTimeButton.isEnabled = enabled
            it.repeatCheckBox.isClickable = enabled
            it.editDateButton.isEnabled = enabled
            it.checkBoxNotification.isClickable = enabled
        }
    }
}