package com.private_projects.tenews.utils

import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import java.io.InputStreamReader
import java.net.URL

class RssCommon(private val rss: String) {
    private var success = false
    private var feed: SyndFeed? = null
    fun get(): List<SyndEntry>? {
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
        return feed?.entries
    }
    fun isSuccess(): Boolean {
        return success
    }
}