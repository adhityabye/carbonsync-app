package io.apaaja.carbonsync.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.apaaja.carbonsync.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryItemAdapter(
    private val historyList: ArrayList<HistoryItem>,
    private val onItemClick: (HistoryItem) -> Unit
) : RecyclerView.Adapter<HistoryItemViewHolder>() {
    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
        fun parseHistoryItemDate(date: LocalDate): String {
            return dateFormat.format(date)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        val currentItem = historyList[position]
        holder.dateView.text = dateFormat.format(currentItem.date)
        holder.valueView.text = String.format("%.1fg", currentItem.value)
        holder.itemView.setOnClickListener { onItemClick(currentItem) }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateData(newList: List<HistoryItem>) {
        historyList.clear()
        historyList.addAll(newList)
        notifyDataSetChanged()
    }
}