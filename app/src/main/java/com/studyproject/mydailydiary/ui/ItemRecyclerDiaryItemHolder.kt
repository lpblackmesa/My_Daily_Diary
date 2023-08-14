package com.studyproject.mydailydiary.ui

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.data.Doing
import com.studyproject.mydailydiary.data.Mood
import com.studyproject.mydailydiary.data.entity.RecycleViewEntity
import com.studyproject.mydailydiary.databinding.ItemRecyclerDiaryitemBinding
import java.text.SimpleDateFormat

class ItemRecyclerDiaryItemHolder private constructor(val binding: ItemRecyclerDiaryitemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RecycleViewEntity) {

        binding.run {

            if (item.data != null) {
                //добавляем в бэйджик цвет настроения
                colorBadge.setBackgroundColor(this.root.context.getColor(Mood.values()[item.data.mood].color))
                //добавляем иконку настроения
                mainIcon.setImageResource(Mood.values()[item.data.mood].iconRes)

                //добавляем иконки занятий

                iconGroup.removeAllViews()  //очищаем LinearLayout
                    //получаем размеры в DP
                val dp20 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20F, this.root.context.resources.displayMetrics).toInt()
                val dp5 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5F, this.root.context.resources.displayMetrics).toInt()


                item.data.doing.forEach {
                    val icon = ImageView(binding.root.context)
                    icon.setImageResource(Doing.values()[it].iconRes)
                    val divider = View(binding.root.context)
                    //добавляем иконку в LinearLayout с размерами
                    iconGroup.addView(icon,LinearLayout.LayoutParams(dp20,dp20))
                    iconGroup.addView(divider,LinearLayout.LayoutParams(dp5,dp5))
                }
                time.setText(SimpleDateFormat("hh:mm").format(item.data.date))
                text.setText(item.data.text)
            }
        }
    }

    //статическая функция inflate биндинг, вызываемая из адаптера
    companion object {
        fun from(parent: ViewGroup): ItemRecyclerDiaryItemHolder {
            val binding = ItemRecyclerDiaryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ItemRecyclerDiaryItemHolder(binding)
        }
    }
}