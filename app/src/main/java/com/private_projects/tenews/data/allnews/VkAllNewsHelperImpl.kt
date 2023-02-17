package com.private_projects.tenews.data.allnews

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.domain.allnews.VkAllNewsHelper
import com.private_projects.tenews.domain.ferra.VkFerraApi
import com.private_projects.tenews.domain.ixbt.VkIxbtApi
import com.private_projects.tenews.domain.tdnews.VkTdnewsApi
import com.private_projects.tenews.utils.VkDataToAllNewsDTOConverter

class VkAllNewsHelperImpl(
    private val vkIxbtApi: VkIxbtApi,
    private val vkFerraApi: VkFerraApi,
    private val vkTdnewsApi: VkTdnewsApi
) : VkAllNewsHelper {
    override fun getNews(): LiveData<PagingData<VkAllNewsDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = VkHelpData.ALL_NEWS_PAGE_SIZE,
                enablePlaceholders = true,
                initialLoadSize = VkHelpData.INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = {
                VkAllNewsPagingSource(
                    VkDataToAllNewsDTOConverter(
                        vkIxbtApi,
                        vkFerraApi,
                        vkTdnewsApi
                    )
                )
            },
            initialKey = 1
        ).liveData
    }

}