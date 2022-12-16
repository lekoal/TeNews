package com.private_projects.tenews.data.htmlparse

import com.private_projects.tenews.domain.htmlparse.ElementsReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.Connection
import org.jsoup.Connection.Response
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class FerraElementsReceiverImpl : ElementsReceiver {
    private var response: Response? = null

    override fun get(newsUrl: String): Flow<Elements> = flow {
        try {
            val connection: Connection = Jsoup.connect((newsUrl))
            connection.userAgent("Chrome/107.0.5304.88 Safari/537.36")
            connection.referrer("http://www.google.com")
            connection.method(Connection.Method.GET)
            response = connection.execute()
            val document = connection.url(newsUrl).get()
            document.let { doc ->
                emit(doc.select("div._2H76iO6B"))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun response(): Response? {
        return response
    }
}