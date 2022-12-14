package com.private_projects.tenews.data.ferra

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.domain.ferra.VkFerraApi
import com.private_projects.tenews.domain.ferra.VkFerraApiHelper

class VkFerraApiHelperImpl(private val vkFerraApi: VkFerraApi) : VkFerraApiHelper {
    override fun getNews(): LiveData<PagingData<VkFerraDTO.Response.Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = VkHelpData.PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = 5
            ),
            pagingSourceFactory = { VkFerraPagingSource(vkFerraApi) },
            initialKey = 1
        ).liveData
    }
}