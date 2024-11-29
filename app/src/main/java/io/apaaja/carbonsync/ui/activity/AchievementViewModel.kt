package io.apaaja.carbonsync.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AchievementViewModel : ViewModel() {
    private val _totalTarget = MutableLiveData<Int>(100000)
    val totalTarget: LiveData<Int> = _totalTarget

    private val _highestTarget = MutableLiveData<Int>(10000)
    val highestTarget: LiveData<Int> = _highestTarget
}