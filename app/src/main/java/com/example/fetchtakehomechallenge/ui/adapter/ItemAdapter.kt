package com.example.fetchtakehomechallenge.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchtakehomechallenge.data.Item

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

        class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById()
        }
}