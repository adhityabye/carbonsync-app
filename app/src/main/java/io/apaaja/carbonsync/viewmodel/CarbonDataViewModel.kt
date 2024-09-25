package io.apaaja.carbonsync.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.apaaja.carbonsync.data.DailyCarbonData
import java.time.LocalDate
import java.util.*
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextFloat

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

    fun useRandomData() {
        val randomData = mutableListOf<DailyCarbonData>()
        val today = LocalDate.now()

        for (i in 0 until 10) {
            val date = today.minusDays(i.toLong())
            val dailyActivities = nextDouble(0.0, 5000.0)
            val transportActivities = nextDouble(0.0, 3000.0)
            randomData.add(DailyCarbonData(date, dailyActivities, transportActivities))
        }

        dailyCarbonDataHistory.value = randomData
        updateCurrentDateCarbonData()
        triggerChange()
    }
}