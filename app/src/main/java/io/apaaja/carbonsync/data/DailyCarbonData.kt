package io.apaaja.carbonsync.data

import java.time.LocalDate

data class DailyCarbonData(var date: LocalDate, var dailyActivities: Double, var transportActivities: Double) {
    fun total(): Double {
        return dailyActivities + transportActivities
    }
}
