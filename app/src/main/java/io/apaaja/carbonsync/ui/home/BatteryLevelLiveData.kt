package io.apaaja.carbonsync.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.content.BroadcastReceiver

class BatteryLevelLiveData(context: Context) : LiveData<Int>() {
    private var context: Context? = context
    private val batteryReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            val batteryPct = (level / scale.toFloat() * 100).toInt()
            value = batteryPct
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context!!.registerReceiver(batteryReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context!!.unregisterReceiver(batteryReceiver)
    }
}