package io.apaaja.carbonsync.repository

import io.apaaja.carbonsync.dao.CarbonActivityDao
import io.apaaja.carbonsync.data.CarbonReductionActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarbonActivitiesRepository(private val userActivitiesDao: CarbonActivityDao) {

    suspend fun insertActivity(activity: CarbonReductionActivity) {
        withContext(Dispatchers.IO) {
            userActivitiesDao.insert(activity)
        }
    }

    suspend fun getAllActivities(): List<CarbonReductionActivity> {
        return withContext(Dispatchers.IO) {
            userActivitiesDao.getAllActivities()
        }
    }

    suspend fun deleteAllActivities() {
        withContext(Dispatchers.IO) {
            userActivitiesDao.deleteAllActivities()
        }
    }
}