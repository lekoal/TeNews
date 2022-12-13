package com.private_projects.tenews.domain.ferra

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.private_projects.tenews.data.ferra.VkFerraDTO

interface VkFerraApiHelper {
    fun getNews(): LiveData<PagingData<VkFerraDTO.Response.Item>>
}