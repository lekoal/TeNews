package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.utils.ferra.FerraDetailsHtmlConverter
import com.private_projects.tenews.utils.ixbt.IxbtDetailsEntityReceiver
import com.private_projects.tenews.utils.tdnews.TdnewsDetailsHtmlConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommonHtmlConverter {
    fun convert(params: List<String>): Flow<NewsDetailsEntity> = flow {
        val newsUrl = params[0]
        val newsId = params[1].toInt()
        val ixbtConverter = IxbtDetailsEntityReceiver()
        val ferraConverter = FerraDetailsHtmlConverter()
        val tdnewsConverter = TdnewsDetailsHtmlConverter()
        params.let { list ->
            when (list[2]) {
                VkHelpData.IXBT_DOMAIN -> {
                    ixbtConverter.convert(newsUrl, newsId).collect {
                        emit(it)
                    }
                }
                VkHelpData.FERRA_DOMAIN -> {
//                    emit(ferraConverter.convert(newsUrl, newsId))
                }
                VkHelpData.TDNEWS_DOMAIN -> {
//                    emit(tdnewsConverter.convert(newsUrl, newsId))
                }
            }
        }
    }
}