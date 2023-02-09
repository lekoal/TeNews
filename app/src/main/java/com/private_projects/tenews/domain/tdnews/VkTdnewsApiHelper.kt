package com.private_projects.tenews.domain.tdnews

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.private_projects.tenews.data.tdnews.VkTdnewsDTO

interface VkTdnewsApiHelper {
    fun getNews(): LiveData<PagingData<VkTdnewsDTO.Response.Item>>
}