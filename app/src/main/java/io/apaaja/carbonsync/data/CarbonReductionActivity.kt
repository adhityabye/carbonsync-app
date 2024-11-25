package io.apaaja.carbonsync.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.apaaja.carbonsync.utils.converter.LocalDateConverter
import java.lang.IllegalArgumentException
import java.time.LocalDate

@Entity(tableName = "user_activities")
data class CarbonReductionActivity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val activity: String,
    val value: Int
) {
    private fun tryParseDailyActivity(activity: String): DailyActivitiesCarbonReduction? {
        return try {
            DailyActivitiesCarbonReduction.valueOf(activity)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    private fun tryParseTravelActivity(activity: String): TravelActivitiesCarbonReduction? {
        return try {
            TravelActivitiesCarbonReduction.valueOf(activity)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    val parsedDate: LocalDate?
        get() = LocalDateConverter.toLocalDate(date)

    fun getCarbonReduction(): Int {
        return tryParseDailyActivity(activity)?.carbonReduction?.times(value)
            ?: tryParseTravelActivity(activity)?.carbonReductionPerKilometer?.times(value)
                ?.div(1000) ?: 0
    }
}