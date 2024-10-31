package io.apaaja.carbonsync.data

import io.apaaja.carbonsync.R
import java.time.LocalDate

data class DailyCarbonData(
    var date: LocalDate,
    var dailyActivities: MutableList<DailyActivityType>,
    var transportActivities: MutableList<Pair<TravelMethodCarbonReduction, Float>>
) {
    companion object {
        enum class DailyActivityType(val carbonReduction: Int) {
            PlantBasedMeal(3000),
            AirDrying(1500)
        }

        enum class TravelMethodCarbonReduction(val carbonReductionPerKilometer: Int) {
            Car(0),
            Motorbike(57),
            Bus(74),
            ElectricCar(124),
            Train(136)
        }

        // calculation
        fun calculateDailyActivities(dailyActivities: MutableList<DailyActivityType>): Int {
            return dailyActivities.sumOf { type -> type.carbonReduction }
        }

        fun calculateTravelActivities(travelActivities: MutableList<Pair<TravelMethodCarbonReduction, Float>>): Int {
            return travelActivities.sumOf { (type, value) ->
                (type.carbonReductionPerKilometer * value).toInt()
            }
        }
    }

    fun total(): Int {
        return calculateDailyActivities(dailyActivities) + calculateTravelActivities(transportActivities)
    }

    fun totalString(): String {
        val total = this.total()
        return if (total < 1000) "$total"
        else String.format("%.2fk", total / 1000.0)
    }
}
