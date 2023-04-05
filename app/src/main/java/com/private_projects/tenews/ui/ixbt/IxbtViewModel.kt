package com.private_projects.tenews.ui.ixbt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.private_projects.tenews.data.ixbt.VkIxbtDTO
import com.private_projects.tenews.domain.ixbt.VkIxbtApiHelper

class IxbtViewModel(private val apiHelper: VkIxbtApiHelper) : ViewModel() {
    fun getIxbtNews(): LiveData<PagingData<VkIxbtDTO.Response.Item>> {
        return apiHelper.getNews()
    }
}