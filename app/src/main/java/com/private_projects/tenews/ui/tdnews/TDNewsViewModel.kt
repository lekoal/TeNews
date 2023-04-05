package com.private_projects.tenews.ui.tdnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.rssnews.RssDTO
import com.private_projects.tenews.data.rssnews.RssDataSource
import com.private_projects.tenews.utils.RssCommon

class TDNewsViewModel(private val rssCommon: RssCommon) : ViewModel() {
    fun getTDNews(): LiveData<PagingData<RssDTO>> {
        return Pager(PagingConfig(pageSize = VkHelpData.PAGE_SIZE)) {
            RssDataSource(rssCommon)
        }.liveData.cachedIn(viewModelScope)
    }
}