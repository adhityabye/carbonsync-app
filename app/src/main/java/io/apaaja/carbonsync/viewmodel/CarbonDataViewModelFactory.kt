package io.apaaja.carbonsync.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.apaaja.carbonsync.repository.CarbonActivitiesRepository

class CarbonDataViewModelFactory(private val repository: CarbonActivitiesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarbonDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarbonDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}