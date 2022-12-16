package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.utils.ferra.FerraDetailsEntityReceiver
import com.private_projects.tenews.utils.ixbt.IxbtDetailsEntityReceiver
import com.private_projects.tenews.utils.tdnews.TdnewsDetailsEntityReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommonHtmlConverter {
    fun convert(params: List<String>): Flow<NewsDetailsEntity> = flow {
        val newsUrl = params[0]
        val newsId = params[1].toInt()
        val newsDate = params[3]
        val ixbtReceiver = IxbtDetailsEntityReceiver()
        val ferraReceiver = FerraDetailsEntityReceiver()
        val tdnewsReceiver = TdnewsDetailsEntityReceiver()
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
//                    emit(tdnewsConverter.convert(newsUrl, newsId))
                }
            }
        }
    }
}