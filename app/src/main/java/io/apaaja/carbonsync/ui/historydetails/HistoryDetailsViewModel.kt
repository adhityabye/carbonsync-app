package io.apaaja.carbonsync.ui.historydetails

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.apaaja.carbonsync.ui.home.HomeViewModel
import io.apaaja.carbonsync.ui.home.ScreenTimeLiveData
import java.time.LocalDate

class HistoryDetailsViewModel(context: Context)  : ViewModel() {
    private val _date = MutableLiveData<LocalDate>()
    val date: LiveData<LocalDate> = _date

    var screenTime: ScreenTimeLiveData = ScreenTimeLiveData(context)

    fun setDate(date: LocalDate) {
        _date.value = date
    }
}