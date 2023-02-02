package com.shudss00.gigachat.presentation.messenger.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shudss00.gigachat.R
import com.shudss00.gigachat.presentation.messenger.listitems.DateItem

class DateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val textViewDate = view.findViewById<TextView>(R.id.textView_dateSeparator)
    fun bind(item: DateItem) {
        textViewDate.text = item.date
    }
}