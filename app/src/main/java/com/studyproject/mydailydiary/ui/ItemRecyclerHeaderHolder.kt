package com.studyproject.mydailydiary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity
import com.studyproject.mydailydiary.databinding.ItemHeaderDateBinding
import com.studyproject.mydailydiary.databinding.ItemRecyclerDiaryitemBinding
import java.text.SimpleDateFormat

class ItemRecyclerHeaderHolder private constructor(val binding: ItemHeaderDateBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecycleViewEntity) {
        binding.headerDate.setText(SimpleDateFormat("EEEE , dd MMMM yyyy   hh:mm").format(item.date))
    }

    //статическая функция inflate биндинг, вызываемая из адаптера
    companion object {
        fun from(parent: ViewGroup): ItemRecyclerHeaderHolder {
            val binding = ItemHeaderDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemRecyclerHeaderHolder(binding)
        }
    }
}
