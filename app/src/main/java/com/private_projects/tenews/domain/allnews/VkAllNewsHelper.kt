package com.private_projects.tenews.domain.allnews

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.private_projects.tenews.data.allnews.VkAllNewsDTO

interface VkAllNewsHelper {
    fun getNews(): LiveData<PagingData<VkAllNewsDTO>>
}