package com.private_projects.tenews.utils

import com.private_projects.tenews.data.VkHelpData
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import java.io.InputStreamReader
import java.net.URL

class RssCommon(private val rss: String) {
    private var success = false
    private var feed: SyndFeed? = null
    fun get(page: Int): List<SyndEntry>? {
        try {
            val rssUrl = URL(rss)
            val rssInputStream = rssUrl.openStream()
            val input = SyndFeedInput()
            feed = input.build(InputStreamReader(rssInputStream))
            success = true
        } catch (e: Exception) {
            e.printStackTrace()
            success = false
        }
        val pagedFeed = feed?.entries?.chunked(VkHelpData.PAGE_SIZE)
        return pagedFeed?.get(page)
    }
    fun isSuccess(): Boolean {
        return success
    }
}