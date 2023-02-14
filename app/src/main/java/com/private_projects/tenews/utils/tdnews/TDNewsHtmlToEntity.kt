package com.private_projects.tenews.utils.tdnews

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.select.Elements

class TDNewsHtmlToEntity {
    private var tempTitle = ""
    fun convert(elements: Elements, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> = flow {
        val texts = mutableListOf<TextBlockEntity>()
        val images = mutableListOf<ImageBlockEntity>()
        val videos = mutableListOf<VideoBlockEntity>()
        elements.forEach { element ->
            if (element.selectFirst("h1")?.text() != null) {
                tempTitle = element.selectFirst("h1")?.text().toString()
            }
            element.select("div.js-mediator-article > *").forEachIndexed { index, elem ->
                elem.select("img").forEach { img ->
                    images.add(
                        ImageBlockEntity(
                            ownerId = newsId,
                            position = index,
                            url = img.attr("src")
                        )
                    )
                }

                elem.select("p").forEach { p ->
                    texts.add(
                        TextBlockEntity(
                            ownerId = newsId,
                            position = index,
                            content = p.text().toString()
                        )
                    )
                }
            }
        }
        emit(
            NewsDetailsEntity(
                HeaderBlockEntity(
                    newsId = newsId,
                    ownerDomain = VkHelpData.TDNEWS_DOMAIN,
                    firstTitle = tempTitle,
                    secondTitle = null,
                    newsDate = newsDate
                ),
                texts,
                images,
                videos
            )
        )
    }
}