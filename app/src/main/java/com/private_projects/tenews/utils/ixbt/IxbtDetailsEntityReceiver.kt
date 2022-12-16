package com.private_projects.tenews.utils.ixbt

import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.data.htmlparse.IxbtElementsReceiverImpl
import com.private_projects.tenews.domain.htmlparse.ElementsReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IxbtDetailsEntityReceiver {
    suspend fun convert(newsUrl: String, newsId: Int): Flow<NewsDetailsEntity> = flow {
        val elementsReceiver: ElementsReceiver = IxbtElementsReceiverImpl()
        val htmlToEntity = IxbtHtmlToEntity()
        elementsReceiver.get(newsUrl).collect { elements ->
            htmlToEntity.convert(elements, newsId).collect { entity ->
                emit(entity)
            }
        }
    }
}