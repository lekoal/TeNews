package com.private_projects.tenews.ui.allnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.private_projects.tenews.data.allnews.VkAllNewsDTO
import com.private_projects.tenews.domain.allnews.VkAllNewsHelper

class AllNewsViewModel(private val allNewsHelper: VkAllNewsHelper) : ViewModel() {
    fun getAllNews(): LiveData<PagingData<VkAllNewsDTO>> {
        return allNewsHelper.getNews()
    }

}