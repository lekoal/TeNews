package com.private_projects.tenews.data.tdnews

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.domain.tdnews.VkTdnewsApi
import com.private_projects.tenews.domain.tdnews.VkTdnewsApiHelper

class VkTdnewsApiHelperImpl(private val vkTdnewsApi: VkTdnewsApi) : VkTdnewsApiHelper {
    override fun getNews(): LiveData<PagingData<VkTdnewsDTO.Response.Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = VkHelpData.PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = VkHelpData.INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { VkTdnewsPagingSource(vkTdnewsApi) },
            initialKey = 1
        ).liveData
    }
}