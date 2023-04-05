package com.private_projects.tenews.ui.ferra

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.rssnews.RssDTO
import com.private_projects.tenews.data.rssnews.RssDataSource
import com.private_projects.tenews.utils.RssCommon

class FerraViewModel(private val rssCommon: RssCommon) : ViewModel() {
    fun getRss(): LiveData<PagingData<RssDTO>> {
        return Pager(PagingConfig(pageSize = VkHelpData.PAGE_SIZE)) {
            RssDataSource(rssCommon)
        }.liveData.cachedIn(viewModelScope)
    }
}