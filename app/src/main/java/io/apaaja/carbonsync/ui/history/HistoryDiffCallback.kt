package io.apaaja.carbonsync.ui.history

import androidx.recyclerview.widget.DiffUtil
import java.time.LocalDate

class HistoryDiffCallback(
    private val oldList: List<Pair<LocalDate, Int>>,
    private val newList: List<Pair<LocalDate, Int>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].first == newList[newItemPosition].first
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem.first == newItem.first && oldItem.second != newItem.second -> {
                PAYLOAD_VALUE_CHANGED
            }
            else -> null
        }
    }

    companion object {
        const val PAYLOAD_VALUE_CHANGED = "PAYLOAD_VALUE_CHANGED"
    }
}