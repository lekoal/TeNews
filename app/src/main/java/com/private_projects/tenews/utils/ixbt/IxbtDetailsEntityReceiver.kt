package com.private_projects.tenews.utils.ixbt

import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.data.htmlparse.IxbtElementsReceiverImpl
import com.private_projects.tenews.domain.htmlparse.ElementsReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IxbtDetailsEntityReceiver {
    suspend fun receive(newsUrl: String, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> =
        flow {
            val elementsReceiver: ElementsReceiver = IxbtElementsReceiverImpl()
            val htmlToEntity = IxbtHtmlToEntity()
            elementsReceiver.get(newsUrl).collect { elements ->
                htmlToEntity.convert(elements, newsId, newsDate).collect { entity ->
                    emit(entity)
                }
            }
        }
}