package io.apaaja.carbonsync.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.apaaja.carbonsync.dao.CarbonActivityDao
import io.apaaja.carbonsync.data.CarbonReductionActivity
import io.apaaja.carbonsync.utils.converter.LocalDateConverter

@Database(entities = [CarbonReductionActivity::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CarbonActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}