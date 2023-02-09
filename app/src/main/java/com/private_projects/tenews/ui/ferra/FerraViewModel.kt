package com.private_projects.tenews.ui.ferra

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.private_projects.tenews.data.ferra.VkFerraDTO
import com.private_projects.tenews.domain.ferra.VkFerraApiHelper

class FerraViewModel(private val apiHelper: VkFerraApiHelper) : ViewModel() {
    fun getFerraNews(): LiveData<PagingData<VkFerraDTO.Response.Item>> {
        return apiHelper.getNews()
    }
}