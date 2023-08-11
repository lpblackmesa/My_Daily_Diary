package com.studyproject.mydailydiary.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.databinding.DialogfragmentEditBinding
import com.studyproject.mydailydiary.models.EditDialogViewModel
import com.studyproject.mydailydiary.utils.Doing
import com.studyproject.mydailydiary.utils.Mood
import java.text.SimpleDateFormat
import java.util.Calendar

//наследуемся от DialogFragment
class EditDiaryDialogFragment : DialogFragment() {

    lateinit var binding: DialogfragmentEditBinding
    private val date = Calendar.getInstance().timeInMillis
    private val chipList = ArrayList<Chip>()

    private val diaryModel : EditDialogViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //inflate диалогового фрагмента
        binding = DialogfragmentEditBinding.inflate(LayoutInflater.from(context))
        //список всех чипов

        diaryModel.string.observe(this){
            Log.e("!", "$it observe onCreateDialog")
        }

        diaryModel.diary_mesage.observe(this){
            Log.e("!", "$it observe onCreateDialog")
        }
        Log.e("!",diaryModel.diaryItem.toString() + " onCreateDialog")

        return activity?.let { it ->
            //подключаем кастомный Spinner адаптер к Spinner с двумя разными layout
            val adapter = SpinnerCustomAdapter(requireContext(), Mood.values())
            adapter.setDropDownViewResource(R.layout.item_spinner_dropped)
            binding.spinner.let {
                it.adapter = adapter
                it.setSelection(0, false)
                it.prompt = getString(R.string.mood_choose)
            }

            binding.dateText.text = SimpleDateFormat("EEEE , dd MMMM yyyy   hh:mm").format(date)

            binding.spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    binding.messageEdit.setText("")

                    //получение всех отмеченных занятий
                    chipList.forEachIndexed() { index, it ->
                        if (it.isChecked) {
                            binding.messageEdit.append(
                                it.id.toString() + " ${
                                    requireContext().getString(
                                        Doing.values()[index].description
                                    )
                                }."
                            )
                            binding.messageEdit.append("\n")

                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.messageEdit.setText(binding.spinner.selectedItemId.toString())
                }
            }
            //создаем программно chip, сколько есть ENUM занятий
            enumValues<Doing>().forEachIndexed { index, it ->
                var chip = Chip(requireContext())
                //первые 8 chip помещаем в первый chipGroup, остальные во второй
                if (index < 9) {
                    chip = getLayoutInflater().inflate(
                        R.layout.chip_choise,
                        binding.chipGroup,
                        false
                    ) as Chip
                    binding.chipGroup.addView(chip)
                } else {
                    chip = getLayoutInflater().inflate(
                        R.layout.chip_choise,
                        binding.chipGroup2,
                        false
                    ) as Chip
                    binding.chipGroup2.addView(chip)
                }
                chip.id = index
                chip.setText(it.description)
                chip.isCheckable = true
                chip.setChipIconResource(it.iconRes)
                chip.setChipIconVisible(true)
                //добавляем чип в список чипов
                chipList.add(chip)
            }


            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)
                // Добавляем позитивную кнопку
                .setPositiveButton(R.string.add,
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                // Добавляем  кнопку назад
                .setNegativeButton(R.string.back,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        diaryModel.diary_mesage.observe(this){
            Log.e("!", "$it observe onCreateView")
        }
        Log.e("!",diaryModel.diaryItem.toString() + " onCreateView")


        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onStart() {
        diaryModel.diary_mesage.observe(this){
            Log.e("!", "$it observe onStart")
        }
        Log.e("!",diaryModel.diaryItem.toString() + " onStart")
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //заполнение фрагмента данными, если они есть

        Log.e("!",diaryModel.diaryItem.toString() + " onViewCreated")

        val showMode = arguments?.getString("MODE") ?: "NULL"



        diaryModel.diary_mesage.observe(this){
            Log.e("!", "$it observe onCreateView")
        }
        Log.e("!",diaryModel.diaryItem.toString() + " onCreateView")



        if (diaryModel.diaryItem != null) {
            diaryModel.diaryItem?.doing?.let { setSelectedChip(it) }
        }
    }


    private fun setSelectedChip(moods : ArrayList<Int>) {

        moods.forEachIndexed { index, it ->
            chipList[index].isChecked = true
            binding.messageEdit.append(it.toString())
        }
    }

}