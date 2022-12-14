package com.private_projects.tenews.data.htmlparse

import com.private_projects.tenews.domain.htmlparse.ElementsReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Connection
import org.jsoup.Connection.Response
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException

class IxbtElementsReceiverImpl: ElementsReceiver {
    private var response: Response? = null

    override fun get(newsUrl: String): Flow<List<Element>> = flow {
        try {
            val tempList = mutableListOf<Element>()
            val connection: Connection = Jsoup.connect(newsUrl)
            connection.userAgent("Chrome/107.0.5304.88 Safari/537.36")
            connection.referrer("http://www.google.com")
            connection.method(Connection.Method.GET)
            response = connection.execute()
            val document = connection.url(newsUrl).get()
            document.let { doc ->
                val elements = doc.select("div.b-article")
                elements.forEach { element ->
                    tempList.add(element)
                }
            }
            emit(tempList)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun response(): Response? {
        return response
    }

}