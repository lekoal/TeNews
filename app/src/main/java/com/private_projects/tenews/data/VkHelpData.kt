package com.private_projects.tenews.data

import com.private_projects.tenews.BuildConfig

object VkHelpData {
    const val VK_API = BuildConfig.VK_API_KEY
    const val BASE_URL = "https://api.vk.com/method/"
    const val IXBT_DOMAIN = "ixbt.com"
    const val IXBT_ID = "-39447662"
    const val FERRA_DOMAIN = "ferra.ru"
    const val FERRA_RSS = "https://www.ferra.ru/exports/rss.xml"
    const val TDNEWS_DOMAIN = "3dnews.ru"
    const val TDNEWS_RSS = "https://3dnews.ru/news/rss/"
    const val API_V = "5.131"
    const val PAGE_SIZE = 15
    const val ALL_NEWS_PAGE_SIZE = PAGE_SIZE * 3
    const val INITIAL_LOAD_SIZE = 8
}