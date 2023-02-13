package com.private_projects.tenews.utils.ferra

import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.data.htmlparse.FerraElementsReceiverImpl
import com.private_projects.tenews.domain.htmlparse.ElementsReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class FerraDetailsEntityReceiver {

    private val elementsReceiver: ElementsReceiver by lazy {
        FerraElementsReceiverImpl()
    }
    suspend fun receive(newsUrl: String, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> =
        flow {
            val htmlToEntity = FerraHtmlToEntity()
            elementsReceiver.get(newsUrl).collect { elements ->
                htmlToEntity.convert(elements, newsId, newsDate).collect { entity ->
                    emit(entity)
                }
            }
        }

    fun getStatus(): StateFlow<Boolean> {
        return elementsReceiver.getStatus()
    }
}