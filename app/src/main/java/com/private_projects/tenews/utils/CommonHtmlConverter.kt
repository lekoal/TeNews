package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.NewsDetailsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommonHtmlConverter {
    fun convert(params: List<String>): Flow<NewsDetailsEntity> = flow {
        val newsUrl = params[0]
        val newsId = params[1].toInt()
        val ixbtConverter = IxbtDetailsHtmlConverter()
        val ferraConverter = FerraDetailsHtmlConverter()
        val tdnewsConverter = TdnewsDetailsHtmlConverter()
        params.let { list ->
            when (list[3]) {
                VkHelpData.IXBT_DOMAIN -> {
                    emit(ixbtConverter.convert(newsUrl, newsId))
                }
                VkHelpData.FERRA_DOMAIN -> {
                    emit(ferraConverter.convert(newsUrl, newsId))
                }
                VkHelpData.TDNEWS_DOMAIN -> {
                    emit(tdnewsConverter.convert(newsUrl, newsId))
                }
            }
        }
    }
}