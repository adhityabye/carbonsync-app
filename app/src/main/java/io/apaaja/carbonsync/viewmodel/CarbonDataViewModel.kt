package io.apaaja.carbonsync.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.apaaja.carbonsync.data.DailyCarbonData
import java.time.LocalDate

class CarbonDataViewModel: ViewModel() {
    val dailyCarbonDataHistory = MutableLiveData<MutableList<DailyCarbonData>>()
    private val _currentDateCarbonData = MutableLiveData<DailyCarbonData>()
    val currentDateCarbonData: LiveData<DailyCarbonData> = _currentDateCarbonData

    init {
        dailyCarbonDataHistory.value = mutableListOf()
        updateCurrentDateCarbonData()
    }

    private fun triggerChange() {
        dailyCarbonDataHistory.value = dailyCarbonDataHistory.value
    }

    private fun updateCurrentDateCarbonData(): DailyCarbonData {
        val currentDate = LocalDate.now()
        val currentData = dailyCarbonDataHistory.value?.find { it.date == currentDate }

        val data = if (currentData != null) {
            currentData
        } else {
            val newData = DailyCarbonData(currentDate, 0.0, 0.0)
            dailyCarbonDataHistory.value?.add(newData)
            triggerChange()
            newData
        }

        _currentDateCarbonData.value = data
        return data
    }
}