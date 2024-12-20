package io.apaaja.carbonsync.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.apaaja.carbonsync.R
import io.apaaja.carbonsync.utils.formatter.IntegerNumberFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

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
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        val currentItem = historyList[position]
        holder.dateView.text = currentItem.first.dayOfMonth.toString()
        holder.monthView.text = currentItem.first.month.getDisplayName(TextStyle.SHORT, Locale.US)
        holder.dayView.text = currentItem.first.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US)
        holder.valueView.text = IntegerNumberFormatter.condense(currentItem.second)
        holder.itemView.setOnClickListener { onItemClick(currentItem) }
    }

    override fun getItemCount(): Int = historyList.size

    fun updateData(newList: List<Pair<LocalDate, Int>>) {
        val sortedNewList = newList.sortedByDescending { it.first }
        val diffCallback = HistoryDiffCallback(historyList, sortedNewList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        historyList = sortedNewList
        diffResult.dispatchUpdatesTo(this)
    }
}

