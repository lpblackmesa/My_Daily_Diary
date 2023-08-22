package com.studyproject.mydailydiary.ui.spinnerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.studyproject.mydailydiary.data.Mood
import com.studyproject.mydailydiary.databinding.ItemSpinnerBinding
import com.studyproject.mydailydiary.databinding.ItemSpinnerDroppedBinding


class SpinnerCustomAdapter(context: Context, val moods: Array<Mood>) :
    ArrayAdapter<Mood>(context, 0, moods) {

    //inflate layout свернутого списка
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.moodIcon.setImageResource(moods[position].iconRes)
        return binding.root
    }

    //inflate layout развернутого списка
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerDroppedBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.moodView.setImageResource(moods[position].iconRes)
        binding.moodText.text = context.getString(moods[position].description)
        return binding.root
    }
}