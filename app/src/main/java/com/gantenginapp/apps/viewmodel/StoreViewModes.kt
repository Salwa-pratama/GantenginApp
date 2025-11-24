package com.gantenginapp.apps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gantenginapp.apps.remote.ApiService
import com.gantenginapp.apps.remote.AllStores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class StoreViewModes(private val api: ApiService) : ViewModel() {

    private val _storeData = MutableStateFlow<List<AllStores>>(emptyList())
    val storeData: StateFlow<List<AllStores>> = _storeData

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadStores()
    }

    fun loadStores() {
        viewModelScope.launch {
            try {
                Log.d("STORE_DEBUG", "Requesting API...")

                val result = api.getAllStore()   // 🔥 MANGGIL API

                Log.d("STORE_DEBUG", "Response count: ${result.size}")
                Log.d("STORE_DEBUG", result.toString())

                _storeData.value = result        // 🔥 UPDATE STATE
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("STORE_ERROR", "Error fetching store data", e)
            }
        }
    }
}
