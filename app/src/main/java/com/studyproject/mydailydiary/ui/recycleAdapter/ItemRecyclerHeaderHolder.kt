package com.studyproject.mydailydiary.ui.recycleAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity
import com.studyproject.mydailydiary.databinding.ItemHeaderDateBinding
import java.text.SimpleDateFormat

class ItemRecyclerHeaderHolder private constructor(val binding: ItemHeaderDateBinding) : RecyclerView.ViewHolder(binding.root) {
    // позиции и ключа выбора элемента associated with our view holder
    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }


    fun bind(item: RecycleViewEntity, listener : HolderItemClickListener) {
        binding.headerDate.setText(SimpleDateFormat("EEEE , dd MMMM yyyy").format(item.date))
        binding.addIcon.setOnClickListener {
            listener.onHolderItemClick(item)
        }


    }

    //статическая функция inflate биндинг, вызываемая из адаптера
    companion object {
        fun from(parent: ViewGroup): ItemRecyclerHeaderHolder {
            val binding = ItemHeaderDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemRecyclerHeaderHolder(binding)
        }
    }
}
