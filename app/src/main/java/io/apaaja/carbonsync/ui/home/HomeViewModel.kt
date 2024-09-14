package io.apaaja.carbonsync.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _carbonReduction = MutableLiveData<Float>().apply {
        value = 0.0f
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val carbonReduction: LiveData<Float> = _carbonReduction
}