package com.private_projects.tenews.utils.ixbt

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.select.Elements

class IxbtHtmlToEntity {
    fun convert(elements: Elements, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> = flow {
        elements.forEach { element ->
            val texts = mutableListOf<TextBlockEntity>()
            val images = mutableListOf<ImageBlockEntity>()
            val videos = mutableListOf<VideoBlockEntity>()
            val header = HeaderBlockEntity(
                newsId = newsId,
                ownerDomain = VkHelpData.IXBT_DOMAIN,
                firstTitle = element.selectFirst("h1")?.text().toString(),
                secondTitle = element.selectFirst("h4")?.text().toString(),
                newsDate = newsDate
            )

            element.select("div.b-article__content > *").forEachIndexed { index, elem ->
                elem.select("div.image-center").let {
                    if (it.select("a").attr("href") != "") {
                        images.add(
                            ImageBlockEntity(
                                ownerId = newsId,
                                position = index,
                                url = "https://www.ixbt.com" + it.select("a")
                                    .attr("href")
                            )
                        )
                    }
                }
                elem.select("p").forEach {
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