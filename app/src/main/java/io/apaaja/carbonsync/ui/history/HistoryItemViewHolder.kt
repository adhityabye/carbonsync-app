package io.apaaja.carbonsync.ui.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.apaaja.carbonsync.R

class HistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dateView: TextView = itemView.findViewById(R.id.textview_date)
    val valueView: TextView = itemView.findViewById(R.id.textview_value)

}