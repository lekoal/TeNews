package com.private_projects.tenews.utils.tdnews

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.select.Elements

class TDNewsHtmlToEntity {
    fun convert(elements: Elements, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> = flow {
        elements.forEach { element ->
            val texts = mutableListOf<TextBlockEntity>()
            val images = mutableListOf<ImageBlockEntity>()
            val videos = mutableListOf<VideoBlockEntity>()
            val header = HeaderBlockEntity(
                newsId = newsId,
                ownerDomain = VkHelpData.TDNEWS_DOMAIN,
                firstTitle = element.selectFirst("h1")?.text().toString(),
                secondTitle = element.selectFirst("p")?.text().toString(),
                newsDate = newsDate
            )

            element.select("div.entry-body").forEachIndexed { index, elem ->
                elem.select("div.source-wrapper").let {
                    if (it.select("img").attr("src") != "") {
                        images.add(
                            ImageBlockEntity(
                                ownerId = newsId,
                                position = index,
                                url = it.select("img").attr("src")
                            )
                        )
                    }
                }
                elem.select("p").forEach {
                    if (it.text().toString() != "" &&
                        it.text().toString() != header.secondTitle &&
                                it.text().toString() != "Источник:"
                    ) {
                        texts.add(
                            TextBlockEntity(
                                ownerId = newsId,
                                position = index,
                                content = it.text().toString()
                            )
                        )
                    }
                }
            }
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