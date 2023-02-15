package com.private_projects.tenews.data.ixbt

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.domain.ixbt.VkIxbtApi
import com.private_projects.tenews.domain.ixbt.VkIxbtApiHelper

class VkIxbtApiHelperImpl(private val vkIxbtApi: VkIxbtApi) : VkIxbtApiHelper {
    override fun getNews(): LiveData<PagingData<VkIxbtDTO.Response.Item>> {
        return Pager(
            config = PagingConfig(
                pageSize = VkHelpData.PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = 7
            ),
            pagingSourceFactory = { VkIxbtPagingSource(vkIxbtApi) },
            initialKey = 1
        ).liveData
    }
}