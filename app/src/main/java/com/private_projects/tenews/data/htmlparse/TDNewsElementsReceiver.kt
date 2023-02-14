package com.private_projects.tenews.data.htmlparse

import com.private_projects.tenews.domain.htmlparse.ElementsReceiver
import kotlinx.coroutines.flow.*
import org.jsoup.Connection
import org.jsoup.Connection.Response
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class TDNewsElementsReceiver : ElementsReceiver {
    private var response: Response? = null
    private val _responseFlow = MutableStateFlow(false)
    private val responseFlow = _responseFlow.asStateFlow()

    override fun get(newsUrl: String): Flow<Elements> = flow {
        try {
            val connection: Connection = Jsoup.connect(newsUrl)
            connection.userAgent("Chrome/107.0.5304.88 Safari/537.36")
            connection.referrer("http://www.google.com")
            connection.method(Connection.Method.GET)
            response = connection.execute()
            val document = connection.url(newsUrl).get()
            emit(document.select("div.article-entry > *"))
            _responseFlow.update {
                false
            }
        } catch (e: IOException) {
            _responseFlow.update {
                true
            }
            e.printStackTrace()
        }
    }

    override fun getStatus(): StateFlow<Boolean> {
        return responseFlow
    }

}