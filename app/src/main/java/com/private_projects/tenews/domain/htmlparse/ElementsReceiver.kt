package com.private_projects.tenews.domain.htmlparse

import kotlinx.coroutines.flow.Flow
import org.jsoup.Connection.Response
import org.jsoup.nodes.Element

interface ElementsReceiver {
    fun get(newsUrl: String): Flow<List<Element>>
    fun response(): Response?
}