package io.apaaja.carbonsync.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Duration

class HomeViewModel(context: Context) : ViewModel() {
    private val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    private val _displayName = MutableLiveData<String>().apply {
        value = "User"
    }
    private val _achievements = MutableLiveData<Pair<Int, Int>>().apply {
        value = Pair(0, 0)
    }

    val displayName: LiveData<String> = _displayName
    val screenTime: LiveData<Long> = ScreenTimeLiveData(context)
    val achievements: LiveData<Pair<Int, Int>> = _achievements
    val batteryLevel: LiveData<Int> = BatteryLevelLiveData(context)

    init {
        _displayName.value = prefs.getString("display_name", "User")
        // Register preference change listener
        prefs.registerOnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                "display_name" -> {
                    _displayName.value = sharedPreferences.getString(key, "User")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        prefs.unregisterOnSharedPreferenceChangeListener { _, _ -> }
    }
}