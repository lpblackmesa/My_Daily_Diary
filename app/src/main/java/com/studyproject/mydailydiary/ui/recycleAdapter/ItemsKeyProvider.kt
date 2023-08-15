package com.studyproject.mydailydiary.ui.recycleAdapter

import androidx.recyclerview.selection.ItemKeyProvider

class ItemsKeyProvider(private val adapter: ItemDiaryAdapter) : ItemKeyProvider<Long>(SCOPE_CACHED) {

    override fun getKey(position: Int): Long =
        adapter.currentList[position].id

    override fun getPosition(key: Long): Int {
        return adapter.currentList.indexOfFirst { it.id == key }
    }

}