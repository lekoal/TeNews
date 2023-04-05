package com.private_projects.tenews.data.allnews

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.domain.allnews.VkAllNewsHelper
import com.private_projects.tenews.domain.ixbt.VkIxbtApi
import com.private_projects.tenews.utils.RssCommon
import com.private_projects.tenews.utils.VkDataToAllNewsDTOConverter

class VkAllNewsHelperImpl(
    private val vkIxbtApi: VkIxbtApi,
    private val rssFerra: RssCommon,
    private val rssTDNews: RssCommon
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
                        rssFerra,
                        rssTDNews
                    )
                )
            },
            initialKey = 1
        ).liveData
    }

}