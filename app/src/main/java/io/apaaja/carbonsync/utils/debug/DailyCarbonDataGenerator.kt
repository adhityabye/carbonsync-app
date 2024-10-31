package io.apaaja.carbonsync.utils.debug

import io.apaaja.carbonsync.data.DailyCarbonData
import java.time.LocalDate
import kotlin.random.Random.Default.nextDouble
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt

class DailyCarbonDataGenerator {
    companion object {
        fun random(date: LocalDate): DailyCarbonData {
            val dailyActivities = List(nextInt(5)) {
                DailyCarbonData.Companion.DailyActivityType.values().random()
            }.toMutableList()
            val travelActivities = List(nextInt(5)) {
                DailyCarbonData.Companion.TravelMethodCarbonReduction.values().random() to nextFloat() * 10f
            }.toMutableList()
            return DailyCarbonData(date, dailyActivities, travelActivities)
        }
    }
}