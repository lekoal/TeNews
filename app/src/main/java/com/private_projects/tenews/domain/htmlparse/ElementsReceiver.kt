package com.private_projects.tenews.domain.htmlparse

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jsoup.select.Elements

interface ElementsReceiver {
    fun get(newsUrl: String): Flow<Elements>
    fun getStatus(): StateFlow<Boolean>
}