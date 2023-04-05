package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.allnews.VkAllNewsDTO
import com.private_projects.tenews.domain.ixbt.VkIxbtApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VkDataToAllNewsDTOConverter(
    private val vkIxbtApi: VkIxbtApi,
    private val rssFerra: RssCommon,
    private val rssTDNews: RssCommon
) {
    suspend fun convert(page: Int): List<VkAllNewsDTO> {
        val resultList = mutableListOf<VkAllNewsDTO>()
        ixbtConverter(vkIxbtApi, page).let { list ->
            list.forEach { dto ->
                if (dto.newsUrl.contains("https://www.ixbt.com/news/")) {
                    resultList.add(dto)
                }
            }
        }
        ferraConverter(rssFerra, page).let { list ->
            list.forEach { dto ->
                resultList.add(dto)
            }
        }
        tdnewsConverter(rssTDNews, page).let { list ->
            list.forEach { dto ->
                resultList.add(dto)
            }
        }

        return resultList.sortedByDescending { it.date.toInt() }
    }

    private suspend fun ixbtConverter(vkIxbtApi: VkIxbtApi, page: Int): List<VkAllNewsDTO> {
        val ixbtList = vkIxbtApi.getNews(page = page)
        val ixbtResultList = mutableListOf<VkAllNewsDTO>()
        if (ixbtList.isSuccessful) {
            ixbtList.body()?.response?.items?.forEach { item ->
                val id = item.id
                val ownerDomain = VkHelpData.IXBT_DOMAIN
                val date = item.date.toString()
                val title = item.text.substringBefore("\n")
                val description = item.text.substringAfter("\n")
                var imageUrl = ""
                var newsUrl = ""
                item.attachments.forEach { attachment ->
                    attachment.photo?.sizes?.forEach { size ->
                        if (size.type == "q" || size.type == "r") {
                            imageUrl = size.url
                        }
                    }
                    newsUrl = attachment.link?.url.toString()
                }
                ixbtResultList.add(
                    VkAllNewsDTO(
                        id,
                        ownerDomain,
                        date,
                        title,
                        description,
                        imageUrl,
                        newsUrl
                    )
                )
            }
        }
        return ixbtResultList
    }

    private suspend fun ferraConverter(rssFerra: RssCommon, page: Int): List<VkAllNewsDTO> {
        return withContext(Dispatchers.IO) {
            val ferraList = rssFerra.get(page = page)
            val ferraResultList = mutableListOf<VkAllNewsDTO>()
            if (rssFerra.isSuccess()) {
                ferraList?.forEach { item ->
                    val id = item.title.hashCode()
                    val ownerDomain = VkHelpData.FERRA_DOMAIN
                    val date = (item.publishedDate.time / 1000).toString()
                    val title = item.title
                    val description = item.description.value
                    val newsUrl = item.link
                    val imageUrl = item.enclosures[0].url

                    ferraResultList.add(
                        VkAllNewsDTO(
                            id,
                            ownerDomain,
                            date,
                            title,
                            description,
                            imageUrl,
                            newsUrl
                        )
                    )
                }
            }
            ferraResultList
        }
    }

    private suspend fun tdnewsConverter(rssTDNews: RssCommon, page: Int): List<VkAllNewsDTO> {
        return withContext(Dispatchers.IO) {
            val tdnewsList = rssTDNews.get(page = page)
            val tdnewsResultList = mutableListOf<VkAllNewsDTO>()
            if (rssTDNews.isSuccess()) {
                tdnewsList?.forEach { item ->
                    val id = item.title.hashCode()
                    val ownerDomain = VkHelpData.TDNEWS_DOMAIN
                    val date = (item.publishedDate.time / 1000).toString()
                    val title = item.title
                    val description = item.description.value
                    val newsUrl = item.link
                    val imageUrl = item.enclosures[0].url
                    tdnewsResultList.add(
                        VkAllNewsDTO(
                            id,
                            ownerDomain,
                            date,
                            title,
                            description,
                            imageUrl,
                            newsUrl
                        )
                    )
                }
            }
            tdnewsResultList
        }
    }
}