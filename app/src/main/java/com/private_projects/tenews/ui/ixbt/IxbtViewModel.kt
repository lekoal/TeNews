package com.private_projects.tenews.ui.ixbt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.ixbt.VkIxbtDTO
import com.private_projects.tenews.domain.ixbt.VkIxbtApiHelper
import com.private_projects.tenews.utils.RssCommon
import com.rometools.rome.feed.synd.SyndEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class IxbtViewModel(private val apiHelper: VkIxbtApiHelper) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _rssData = MutableLiveData<List<SyndEntry>>()
    val rssData: LiveData<List<SyndEntry>> = _rssData
    fun getIxbtNews(): LiveData<PagingData<VkIxbtDTO.Response.Item>> {
        return apiHelper.getNews()
    }

    fun getIxbtRss() {
        coroutineScope.launch {
            RssCommon(VkHelpData.IXBT_RSS).get().let { list ->
                _rssData.postValue(list)
            }
        }
    }

    override fun onCleared() {
        coroutineScope.cancel()
        super.onCleared()
    }
}