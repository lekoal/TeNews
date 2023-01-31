package com.private_projects.tenews.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun load(isLoading: Boolean) {
        coroutineScope.launch {
            _loading.postValue(isLoading)
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}