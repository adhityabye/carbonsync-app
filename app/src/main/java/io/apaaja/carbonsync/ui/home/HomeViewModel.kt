package io.apaaja.carbonsync.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.Duration

class HomeViewModel : ViewModel() {
    private val _screenTime = MutableLiveData<Duration>().apply {
        value = Duration.ZERO
    }
    private val _achievements = MutableLiveData<Pair<Int, Int>>().apply {
        value = Pair(0, 0)
    }

    val screenTime: LiveData<Duration> = _screenTime
    val achievements: LiveData<Pair<Int, Int>> = _achievements
}