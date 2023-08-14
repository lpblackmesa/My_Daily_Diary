package com.studyproject.mydailydiary.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity
import com.studyproject.mydailydiary.databinding.ItemHeaderDateBinding
import com.studyproject.mydailydiary.databinding.ItemNotificationBinding

class ItemRecyclerNotificationHolder private constructor(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecycleViewEntity) {
        binding.text.setText(item.data?.text)
    }

    //статическая функция inflate биндинг, вызываемая из адаптера
    companion object {
        fun from(parent: ViewGroup): ItemRecyclerNotificationHolder {
            val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemRecyclerNotificationHolder(binding)
        }
    }
}