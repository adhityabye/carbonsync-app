package io.apaaja.carbonsync.ui.historydetails

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.apaaja.carbonsync.ui.home.HomeViewModel

class HistoryDetailsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryDetailsViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}