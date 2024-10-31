package io.apaaja.carbonsync.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.data.DailyCarbonData
import io.apaaja.carbonsync.utils.formatter.IntegerNumberFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryItemAdapter(
    private val onItemClick: (Pair<LocalDate, Int>) -> Unit
) : RecyclerView.Adapter<HistoryItemViewHolder>() {
    companion object {
        private val dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
        fun parseHistoryItemDate(date: LocalDate): String {
            return dateFormat.format(date)
        }
    }

    private var historyList: List<Pair<LocalDate, Int>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        val currentItem = historyList[position]
        holder.dateView.text = dateFormat.format(currentItem.first)
        holder.valueView.text = IntegerNumberFormatter.condense(currentItem.second)
        holder.itemView.setOnClickListener { onItemClick(currentItem) }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateData(newList: List<Pair<LocalDate, Int>>) {
        historyList = newList.sortedByDescending { it.first }
        notifyDataSetChanged()
    }
}