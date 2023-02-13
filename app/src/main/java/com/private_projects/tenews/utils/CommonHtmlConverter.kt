package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.ui.main.FERRA_NEWS_FRAGMENT
import com.private_projects.tenews.ui.main.IXBT_NEWS_FRAGMENT
import com.private_projects.tenews.utils.ferra.FerraDetailsEntityReceiver
import com.private_projects.tenews.utils.ixbt.IxbtDetailsEntityReceiver
import com.private_projects.tenews.utils.tdnews.TdnewsDetailsEntityReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class CommonHtmlConverter {
    private val ixbtReceiver: IxbtDetailsEntityReceiver by lazy {
        IxbtDetailsEntityReceiver()
    }
    private val ferraReceiver: FerraDetailsEntityReceiver by lazy {
        FerraDetailsEntityReceiver()
    }
    private val tdnewsReceiver: TdnewsDetailsEntityReceiver by lazy {
        TdnewsDetailsEntityReceiver()
    }

    fun convert(params: List<String>): Flow<NewsDetailsEntity> = flow {
        val newsUrl = params[0]
        val newsId = params[1].toInt()
        val newsDate = params[3]
        params.let { list ->
            when (list[2]) {
                VkHelpData.IXBT_DOMAIN -> {
                    ixbtReceiver.receive(newsUrl, newsId, newsDate).collect {
                        emit(it)
                    }
                }
                VkHelpData.FERRA_DOMAIN -> {
                    ferraReceiver.receive(newsUrl, newsId, newsDate).collect {
                        emit(it)
                    }
                }
                VkHelpData.TDNEWS_DOMAIN -> {
                    tdnewsReceiver.receive(newsUrl, newsId, newsDate).collect {
                        emit(it)
                    }
                }
            }
        }
    }

    fun getStatus(domain: Int): StateFlow<Boolean> {
        return when (domain) {
            IXBT_NEWS_FRAGMENT -> ixbtReceiver.getStatus()
            FERRA_NEWS_FRAGMENT -> ferraReceiver.getStatus()
            else -> tdnewsReceiver.getStatus()
        }
    }
}