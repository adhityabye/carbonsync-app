package io.apaaja.carbonsync.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.apaaja.carbonsync.data.CarbonReductionActivity
import io.apaaja.carbonsync.data.DailyCarbonData
import io.apaaja.carbonsync.repository.CarbonActivitiesRepository
import io.apaaja.carbonsync.utils.converter.LocalDateConverter
import io.apaaja.carbonsync.utils.debug.DailyCarbonDataGenerator
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

class CarbonDataViewModel(private val repository: CarbonActivitiesRepository) : ViewModel() {
    private val _activities = MutableLiveData<List<CarbonReductionActivity>>(emptyList())
    val activities: LiveData<List<CarbonReductionActivity>> = _activities

    private val _currentDayTotalCarbonReduction = MutableLiveData(0)
    val currentDayTotalCarbonReduction: LiveData<Int> get() = _currentDayTotalCarbonReduction

    private val _historicalTotalCarbonReduction = MutableLiveData<List<Pair<LocalDate, Int>>>()
    val historicalTotalCarbonReduction: LiveData<List<Pair<LocalDate, Int>>> get() = _historicalTotalCarbonReduction

    val dailyCarbonTarget: LiveData<Int> = MutableLiveData(5500)

    init {
        loadAllActivities()
    }

    private fun insertActivity(activity: CarbonReductionActivity) = viewModelScope.launch {
        repository.insertActivity(activity)
        loadAllActivities()
    }

    private fun deleteAllActivities() = viewModelScope.launch {
        repository.deleteAllActivities()
        loadAllActivities()
    }

    private fun loadAllActivities() = viewModelScope.launch {
        _activities.value = repository.getAllActivities()
        updateCarbonReductionValues()
    }

    private fun updateCarbonReductionValues() {
        _activities.value?.let { activities ->
            val today = LocalDate.now()
            val todayTotal = activities
                .filter { it.parsedDate == today }
                .sumOf { it.getCarbonReduction() }
            _currentDayTotalCarbonReduction.value = todayTotal

            val historicalTotals = activities
                .mapNotNull { activity ->
                    activity.parsedDate?.let { date ->
                        date to activity.getCarbonReduction()
                    }
                }
                .groupBy { it.first }
                .map { entry ->
                    entry.key to entry.value.sumOf { it.second }
                }
                .sortedBy { it.first }

            _historicalTotalCarbonReduction.value = historicalTotals
        }
    }

    fun getTotalForDate(date: LocalDate): Int {
        _activities.value?.let { activities ->
            return activities
                .filter { it.parsedDate == date }
                .sumOf { it.getCarbonReduction() }
        }
        return 0
    }

    fun randomizeData() {
        deleteAllActivities()
        val activities = mutableListOf<CarbonReductionActivity>()
        val activitiesList = listOf(
            "PlantBasedMeal",
            "AirDrying",
            "TravelByCar",
            "TravelByMotorbike",
            "TravelByBus",
            "TravelByElectricCar",
            "TravelByTrain"
        )

        repeat(20) {
            val randomizedDate = LocalDate.now().minusDays(nextLong(5))
            val randomizedActivity = activitiesList.random()
            val randomizedValue = if ("Travel" in randomizedActivity) nextInt(1, 5000) else 1
            activities.add(
                CarbonReductionActivity(
                    date = LocalDateConverter.fromLocalDate(randomizedDate) ?: "",
                    activity = randomizedActivity,
                    value = randomizedValue
                )
            )
        }

        activities.forEach { insertActivity(it) }
        loadAllActivities()
    }
}