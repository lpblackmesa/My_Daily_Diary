package com.studyproject.mydailydiary.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.studyproject.mydailydiary.R
import com.studyproject.mydailydiary.utils.Mood

class SpinnerCustomAdapter(context: Context, val moods: Array<Mood>) : ArrayAdapter<Mood>(context,0,moods) {

    //inflate layout свернутого списка
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.item_spinner, parent,false)
        view.findViewById<ImageView>(R.id.moodIcon)?.setImageResource(moods[position].iconRes)
        return view
    }
//inflate layout развернутого списка
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_spinner_dropped, parent,false)
        view.findViewById<ImageView>(R.id.moodView)?.setImageResource(moods[position].iconRes)
        view.findViewById<TextView>(R.id.moodText)?.text = context.getString(moods[position].description)
        return view
    }
}