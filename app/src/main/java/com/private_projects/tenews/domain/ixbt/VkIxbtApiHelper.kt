package com.private_projects.tenews.domain.ixbt

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.private_projects.tenews.data.ixbt.VkIxbtDTO

interface VkIxbtApiHelper {
    fun getNews(): LiveData<PagingData<VkIxbtDTO.Response.Item>>
}