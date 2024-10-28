package io.apaaja.carbonsync.ui.home

import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import java.util.*

class ScreenTimeLiveData(private val context: Context) : LiveData<Long>() {
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 1000 * 60L

    private val updateScreenTimeTask = object : Runnable {
        override fun run() {
            value = getTodayScreenTime()
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onActive() {
        super.onActive()
        handler.post(updateScreenTimeTask)
    }

    override fun onInactive() {
        super.onInactive()
        handler.removeCallbacks(updateScreenTimeTask)
    }

    private fun getTodayScreenTime(): Long {
        val endTime = System.currentTimeMillis()
        val startTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY, startTime, endTime
        )

        var totalScreenTime = 0L
        for (usageStats in usageStatsList) {
            if (usageStats.firstTimeStamp >= startTime && usageStats.lastTimeStamp <= endTime)
                totalScreenTime += usageStats.totalTimeInForeground
        }
        return totalScreenTime
    }
}