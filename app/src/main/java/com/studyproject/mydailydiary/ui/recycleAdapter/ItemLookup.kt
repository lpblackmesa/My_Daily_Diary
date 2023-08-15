package com.studyproject.mydailydiary.ui.recycleAdapter

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class ItemLookup(private val rv: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent)
            : ItemDetails<Long>? {
        val view = rv.findChildViewUnder(event.x, event.y)
        //если на нажатии есть вью, получаем тип холдера и возвращаем getItemDetails
        if (view != null) {
            when (rv.getChildViewHolder(view)) {

                is ItemRecyclerDiaryItemHolder -> return (rv.getChildViewHolder(view) as ItemRecyclerDiaryItemHolder).getItemDetails()
                is ItemRecyclerHeaderHolder -> return (rv.getChildViewHolder(view) as ItemRecyclerHeaderHolder).getItemDetails()
                is ItemRecyclerNotificationHolder -> return (rv.getChildViewHolder(view) as ItemRecyclerNotificationHolder).getItemDetails()

            }
        }
        return null
    }
}