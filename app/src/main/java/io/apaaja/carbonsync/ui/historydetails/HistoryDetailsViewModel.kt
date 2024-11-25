package io.apaaja.carbonsync.ui.historydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

class HistoryDetailsViewModel : ViewModel() {
    private val _date = MutableLiveData<LocalDate>()
    val date: LiveData<LocalDate> = _date

    fun setDate(date: LocalDate) {
        _date.value = date
    }
}