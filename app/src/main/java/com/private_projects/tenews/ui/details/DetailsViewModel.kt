package com.private_projects.tenews.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.utils.CommonHtmlConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _resultEntity = MutableLiveData<NewsDetailsEntity>()
    val resultEntity: LiveData<NewsDetailsEntity> = _resultEntity

    private val converter: CommonHtmlConverter by lazy {
        CommonHtmlConverter()
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    fun getElements(list: List<String>) {
        _isLoading.postValue(true)
        coroutineScope.launch {
            converter.convert(list).collect { entity ->
                _resultEntity.postValue(entity)
                _isLoading.postValue(false)
            }
        }
    }

    fun getStatus(domain: Int): StateFlow<Boolean> {
        return converter.getStatus(domain)
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}