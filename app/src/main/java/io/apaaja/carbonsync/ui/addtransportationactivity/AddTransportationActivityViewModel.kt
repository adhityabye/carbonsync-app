package io.apaaja.carbonsync.ui.addtransportationactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddTransportationActivityViewModel : ViewModel() {
    private val _selectedTransportMode = MutableLiveData<String?>()
    val selectedTransportMode: LiveData<String?> get() = _selectedTransportMode

    fun setTransportMode(mode: String?) {
        _selectedTransportMode.value = mode
    }
}