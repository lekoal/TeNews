package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.allnews.VkAllNewsDTO
import com.private_projects.tenews.domain.ferra.VkFerraApi
import com.private_projects.tenews.domain.ixbt.VkIxbtApi
import com.private_projects.tenews.domain.tdnews.VkTdnewsApi

class VkDataToAllNewsDTOConverter(
    private val vkIxbtApi: VkIxbtApi,
    private val vkFerraApi: VkFerraApi,
    private val vkTdnewsApi: VkTdnewsApi
) {
    suspend fun convert(page: Int): List<VkAllNewsDTO> {
        val resultList = mutableListOf<VkAllNewsDTO>()
        resultList.addAll(ixbtConverter(vkIxbtApi, page))
        resultList.addAll(ferraConverter(vkFerraApi, page))
        resultList.addAll(tdnewsConverter(vkTdnewsApi, page))
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

    private suspend fun ferraConverter(vkFerraApi: VkFerraApi, page: Int): List<VkAllNewsDTO> {
        val ferraList = vkFerraApi.getNews(page = page)
        val ferraResultList = mutableListOf<VkAllNewsDTO>()
        if (ferraList.isSuccessful) {
            ferraList.body()?.response?.items?.forEach { item ->
                val id = item.id
                val ownerDomain = VkHelpData.FERRA_DOMAIN
                val date = item.date.toString()
                val title = item.attachments[0].link.title
                val description = item.attachments[0].link.description
                val newsUrl = item.attachments[0].link.url
                var imageUrl = ""
                item.attachments[0].link.photo?.sizes?.forEach { size ->
                    if (size.type == "p" || size.type == "l") {
                        imageUrl = size.url
                    }
                }
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
        return ferraResultList
    }

    private suspend fun tdnewsConverter(vkTdnewsApi: VkTdnewsApi, page: Int): List<VkAllNewsDTO> {
        val tdnewsList = vkTdnewsApi.getNews(page = page)
        val tdnewsResultList = mutableListOf<VkAllNewsDTO>()
        if (tdnewsList.isSuccessful) {
            tdnewsList.body()?.response?.items?.forEach { item ->
                val id = item.id
                val ownerDomain = VkHelpData.TDNEWS_DOMAIN
                val date = item.date.toString()
                val title = item.attachments[0].link?.title.toString()
                val description = item.attachments[0].link?.description.toString()
                val newsUrl = item.attachments[0].link?.url.toString()
                var imageUrl = ""
                item.attachments[0].link?.photo?.sizes?.forEach { size ->
                    if (size.type == "p" || size.type == "l") {
                        imageUrl = size.url
                    }
                }
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
        return tdnewsResultList
    }
}