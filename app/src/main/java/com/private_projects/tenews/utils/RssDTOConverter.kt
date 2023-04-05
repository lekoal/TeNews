package com.private_projects.tenews.utils

import com.private_projects.tenews.data.rssnews.RssDTO
import com.rometools.rome.feed.synd.SyndEntry

class RssDTOConverter {
    private val rssData = mutableListOf<RssDTO>()
    private val rssDateFormatter = RssDateFormatter()
    fun convert(sourceList: List<SyndEntry>): List<RssDTO> {
        sourceList.forEach { entry ->
            rssData.add(
                RssDTO(
                    id = entry.title.hashCode(),
                    ownerDomain = entry.author,
                    date = rssDateFormatter.format(entry.publishedDate),
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