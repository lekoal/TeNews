package com.private_projects.tenews.ui.tdnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.private_projects.tenews.data.tdnews.VkTdnewsDTO
import com.private_projects.tenews.domain.tdnews.VkTdnewsApiHelper

class TDNewsViewModel(private val apiHelper: VkTdnewsApiHelper) : ViewModel() {
    fun getTDNews(): LiveData<PagingData<VkTdnewsDTO.Response.Item>> {
        return apiHelper.getNews()
    }
}