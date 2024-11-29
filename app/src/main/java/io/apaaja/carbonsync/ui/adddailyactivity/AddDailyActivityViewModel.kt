package io.apaaja.carbonsync.ui.adddailyactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddDailyActivityViewModel : ViewModel() {
    private val _selectedDailyActivity = MutableLiveData<String?>()
    val selectedDailyActivity: LiveData<String?> get() = _selectedDailyActivity

    fun setDailyActivity(mode: String?) {
        _selectedDailyActivity.value = mode
    }
}