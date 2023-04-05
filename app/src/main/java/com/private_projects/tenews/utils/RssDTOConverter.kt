package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.rssnews.RssDTO
import com.rometools.rome.feed.synd.SyndEntry

class RssDTOConverter {
    private val rssData = mutableListOf<RssDTO>()
    fun convert(sourceList: List<SyndEntry>): List<RssDTO> {
        val domain = if (sourceList[0].link.contains(VkHelpData.FERRA_DOMAIN)) {
            VkHelpData.FERRA_DOMAIN
        } else if (sourceList[0].link.contains(VkHelpData.IXBT_DOMAIN)) {
            VkHelpData.IXBT_DOMAIN
        } else {
            VkHelpData.TDNEWS_DOMAIN
        }
        sourceList.forEach { entry ->
            rssData.add(
                RssDTO(
                    id = entry.title.hashCode(),
                    ownerDomain = domain,
                    date = entry.publishedDate,
                    title = entry.title,
                    description = entry.description.value,
                    imageUrl = entry.enclosures[0].url,
                    newsUrl = entry.link
                )
            )
        }
        return rssData
    }
}