package com.studyproject.mydailydiary.ui.recycleAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.HolderType
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity
import com.studyproject.mydailydiary.databinding.ItemNotificationBinding
import java.text.SimpleDateFormat

class ItemRecyclerNotificationHolder private constructor(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {


    // позиции и ключа выбора элемента associated with our view holder
    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }

    fun bind(item: RecycleViewEntity, selected : Boolean, listener : HolderItemClickListener) {

        binding.run {
            if (selected) {
                frame.setBackgroundResource(R.drawable.frame_checked)
                selectIcon.isVisible = true
            } else {
                frame.setBackgroundResource(R.drawable.frame)
                selectIcon.isVisible = false
            }
            text.text = item.data?.text
            time.text = SimpleDateFormat("HH:mm").format(item.data?.date)
        }
        itemView.setOnClickListener {
            listener.onHolderItemClick(item)
        }
    }



    //статическая функция inflate биндинг, вызываемая из адаптера
    companion object {
        fun from(parent: ViewGroup): ItemRecyclerNotificationHolder {
            val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemRecyclerNotificationHolder(binding)
        }
    }
}