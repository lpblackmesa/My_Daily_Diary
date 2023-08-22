package com.studyproject.mydailydiary.ui.recycleAdapter

import androidx.recyclerview.selection.SelectionTracker
import com.studyproject.mydailydiary.data.HolderType

//переопределенный класс для создания правил, по которым будет решаться, можно ли выделять
//RecyclerView или нет
class CustomSelectionPredicate(private val adapter: ItemDiaryAdapter): SelectionTracker
.SelectionPredicate<Long>() {
    override fun canSelectMultiple() = true

    override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
        //если item - заголовок, запрещаем его выделение
        val item = adapter.currentList.find { it.id == key }
        return  (item?.type != HolderType.HEADER )
    }
    override fun canSetStateAtPosition(position: Int, nextState: Boolean) = true
}