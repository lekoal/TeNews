package com.private_projects.tenews.utils.ferra

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jsoup.select.Elements

class FerraHtmlToEntity {
    fun convert(elements: Elements, newsId: Int, newsDate: String): Flow<NewsDetailsEntity> = flow {
        elements.forEach { element ->
            val texts = mutableListOf<TextBlockEntity>()
            val images = mutableListOf<ImageBlockEntity>()
            val videos = mutableListOf<VideoBlockEntity>()
            val header = HeaderBlockEntity(
                newsId = newsId,
                ownerDomain = VkHelpData.FERRA_DOMAIN,
                firstTitle = element.selectFirst("h1")?.text().toString(),
                secondTitle = element.selectFirst("span.jsx-3350499674")?.text().toString(),
                newsDate = newsDate
            )

            element.select("div._2H76iO6B > *").forEachIndexed { index, elem ->
                elem.select("div._315ylfAj").let {
                    if (it.select("link").attr("href") != "") {
                        images.add(
                            ImageBlockEntity(
                                ownerId = newsId,
                                position = index,
                                url = "https://www.ferra.ru" + it.select("link").attr("href")
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