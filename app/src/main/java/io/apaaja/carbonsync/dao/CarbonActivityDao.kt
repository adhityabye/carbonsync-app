package io.apaaja.carbonsync.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.apaaja.carbonsync.data.CarbonReductionActivity

@Dao
interface CarbonActivityDao {
    @Insert
    suspend fun insert(activity: CarbonReductionActivity)

    @Query("SELECT * FROM user_activities")
    suspend fun getAllActivities(): List<CarbonReductionActivity>

    @Query("DELETE FROM user_activities")
    suspend fun deleteAllActivities()
}