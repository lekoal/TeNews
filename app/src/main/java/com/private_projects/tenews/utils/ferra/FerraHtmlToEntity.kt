package com.private_projects.tenews.utils.ferra

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.select.Elements

class FerraHtmlToEntity {
    private var tempFirstTitle = ""
    private var tempSecondTitle = ""

    fun convert(elements: Elements, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> = flow {
        val texts = mutableListOf<TextBlockEntity>()
        val images = mutableListOf<ImageBlockEntity>()
        val videos = mutableListOf<VideoBlockEntity>()
        elements.forEachIndexed { index, element ->

            if (element.selectFirst("h1")?.text() != null) {
                tempFirstTitle = element.selectFirst("h1")?.text().toString()
            }
            if (element.selectFirst("span.subtitle")?.text() != null) {
                tempSecondTitle = element.selectFirst("span.subtitle")?.text().toString()
            }

            if (!element.select("link").isNullOrEmpty()) {
                if (element.select("link").attr("itemprop") == "url") {
                    images.add(
                        ImageBlockEntity(
                            ownerId = newsId,
                            position = index,
                            url = "https://www.ferra.ru" +
                                    element.select("link").attr("href").toString()
                        )
                    )
                }
            }

            element.select("p").forEach {
                if (it.text().toString() != "") {
                    texts.add(
                        TextBlockEntity(
                            ownerId = newsId,
                            position = index,
                            content = it.text().toString()
                        )
                    )
                }
            }
            if (tempFirstTitle != "" && tempSecondTitle != "") {
                val header = HeaderBlockEntity(
                    newsId = newsId,
                    ownerDomain = VkHelpData.FERRA_DOMAIN,
                    firstTitle = tempFirstTitle,
                    secondTitle = tempSecondTitle,
                    newsDate = newsDate
                )

                println("$header\n\n")
                println("$texts\n\n")
                println("$images\n\n")

                emit(
                    NewsDetailsEntity(
                        header,
                        texts,
                        images,
                        videos
                    )
                )
            }
        }
    }
}