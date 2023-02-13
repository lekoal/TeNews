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

            element.select("link").forEach { link ->
                if (!element.select("link").isNullOrEmpty()) {
                    if (link.attr("itemprop") == "url") {
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
            }

            element.select("p").forEach { p ->
                if (!p.text().isNullOrEmpty()) {
                    texts.add(
                        TextBlockEntity(
                            ownerId = newsId,
                            position = index,
                            content = p.text().toString()
                        )
                    )
                }
            }

            element.select("ul").forEach {ul ->
                ul.select("li").forEach {li ->
                    if (!li.text().isNullOrEmpty()) {
                       texts.add(
                           TextBlockEntity(
                               ownerId = newsId,
                               position = index,
                               content = li.text().toString()
                           )
                       )
                    }
                }
            }
        }
        emit(
            NewsDetailsEntity(
                HeaderBlockEntity(
                    newsId = newsId,
                    ownerDomain = VkHelpData.FERRA_DOMAIN,
                    firstTitle = tempFirstTitle,
                    secondTitle = tempSecondTitle,
                    newsDate = newsDate
                ),
                texts,
                images,
                videos
            )
        )
    }
}