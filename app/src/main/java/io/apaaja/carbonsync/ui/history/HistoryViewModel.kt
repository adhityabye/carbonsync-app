package io.apaaja.carbonsync.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class HistoryViewModel : ViewModel() {
    private val _currentCarbonReduction = MutableLiveData(0.0f);
    private val _historyData = MutableLiveData<List<HistoryItem>>()
    val currentCarbonReduction: LiveData<Float> = _currentCarbonReduction
    val historyData: LiveData<List<HistoryItem>> = _historyData

    init {
        _historyData.value = listOf(
            HistoryItem(LocalDate.now(), 12.5f),
            HistoryItem(LocalDate.now().minusDays(1), 8.0f),
            HistoryItem(LocalDate.now().minusDays(2), 15.0f)
        )
    }

    fun updateHistory(newHistory: List<HistoryItem>) {
        _historyData.value = newHistory
    }
}