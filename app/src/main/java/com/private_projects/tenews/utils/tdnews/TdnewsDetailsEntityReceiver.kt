package com.private_projects.tenews.utils.tdnews

import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.data.htmlparse.TDNewsElementsReceiver
import com.private_projects.tenews.domain.htmlparse.ElementsReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TdnewsDetailsEntityReceiver {
    suspend fun receive(newsUrl: String, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> =
        flow {
            val elementsReceiver: ElementsReceiver = TDNewsElementsReceiver()
            val htmlToEntity = TDNewsHtmlToEntity()
            elementsReceiver.get(newsUrl).collect { elements ->
                htmlToEntity.convert(elements, newsId, newsDate).collect { entity ->
                    emit(entity)
                }
            }
        }
}