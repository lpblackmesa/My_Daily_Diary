package com.studyproject.mydailydiary.ui.recycleAdapter

import com.studyproject.mydailydiary.data.entity.RecycleViewEntity

// интерфейс для клика на item в RecyclerView
interface HolderItemClickListener {
    fun onHolderItemClick(item : RecycleViewEntity)

}


