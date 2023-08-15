package com.studyproject.mydailydiary.ui.recycleAdapter

import android.view.ViewGroup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyproject.mydailydiary.data.HolderType
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity


class ItemDiaryAdapter(private val listener: HolderItemClickListener) :
//передаем интерфейс для клика в параметры
//указываем тип данных в адаптере и ViewHolder
    ListAdapter<RecycleViewEntity, RecyclerView.ViewHolder>(

        object : DiffUtil.ItemCallback<RecycleViewEntity>() {
            override fun areItemsTheSame(
                oldItem: RecycleViewEntity,
                newItem: RecycleViewEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecycleViewEntity,
                newItem: RecycleViewEntity
            ): Boolean {
                return oldItem == newItem
            }
        }) {


    var tracker: SelectionTracker<Long>? = null

    init {
        // нужный параметр, если брать ключ из параметра getItemId
        // все элементы адаптера будут иметь стабильные идентификаторы
        setHasStableIds(true)
    }


    override fun getItemViewType(position: Int): Int {     //тип Вью из своего класса RecycleViewEntity
        return getItem(position).type.ordinal
    }

    // устанавливаем Id для каждого элемента для RecyclerView selection
    override fun getItemId(position: Int): Long = currentList[position].id

    //создаем трекер и делаем функцию для его инициализации

    fun setMyTracker(tracker: SelectionTracker<Long>?) {
        this.tracker = tracker
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


        //если позиция выбрана, выделяем выбранный элемент
        tracker?.let {
            //если holder является определенным типом - вызываем bind
            when (holder) {

                is ItemRecyclerDiaryItemHolder -> holder.bind(
                    getItem(position),
                    it.isSelected(currentList[position].id),listener)

                is ItemRecyclerHeaderHolder -> holder.bind(getItem(position),listener)

                is ItemRecyclerNotificationHolder -> holder.bind(
                    getItem(position),
                    it.isSelected(currentList[position].id),listener)
            }
        }
    }
}


