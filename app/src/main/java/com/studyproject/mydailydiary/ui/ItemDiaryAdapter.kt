package com.studyproject.mydailydiary.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyproject.mydailydiary.data.HolderType
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity



class ItemDiaryAdapter(private val listener: HolderItemClickListener):
//указываем тип данных в адаптере и ViewHolder
    ListAdapter<RecycleViewEntity,RecyclerView.ViewHolder,>(

    //передаем интерфейс для клика в параметры
    object : DiffUtil.ItemCallback<RecycleViewEntity>() {
        override fun areItemsTheSame(oldItem: RecycleViewEntity, newItem: RecycleViewEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RecycleViewEntity, newItem: RecycleViewEntity): Boolean {
            return oldItem == newItem
        }
    }) {
    //устанавливаем тип Вью из своего класса RecycleViewEntity
    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal

    }
//создаем разный ViewHolder в зависимости от типа Вью
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HolderType.DIARY.ordinal -> {
                //вызываем из разных ViewHolder свой inflate
                ItemRecyclerDiaryItemHolder.from(parent)
            }

            HolderType.HEADER.ordinal -> {
                ItemRecyclerHeaderHolder.from(parent)
            }

            HolderType.NOTIFY.ordinal -> {
                ItemRecyclerNotificationHolder.from(parent)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            //если holder является определенным типом - вызываем bind
            is ItemRecyclerDiaryItemHolder -> holder.bind(getItem(position))
            is ItemRecyclerHeaderHolder -> holder.bind(getItem(position))
            is ItemRecyclerNotificationHolder -> holder.bind(getItem(position))

        }
    }
}


