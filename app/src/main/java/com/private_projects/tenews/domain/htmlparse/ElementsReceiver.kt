package com.private_projects.tenews.domain.htmlparse

import kotlinx.coroutines.flow.Flow
import org.jsoup.Connection.Response
import org.jsoup.select.Elements

interface ElementsReceiver {
    fun get(newsUrl: String): Flow<Elements>
    fun response(): Response?
}