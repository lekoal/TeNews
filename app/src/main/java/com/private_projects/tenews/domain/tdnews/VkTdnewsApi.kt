package com.private_projects.tenews.domain.tdnews

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.tdnews.VkTdnewsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VkTdnewsApi {
    @GET("wall.get")
    suspend fun getNews(
        @Query("owner_id") ownerId: String = VkHelpData.TDNEWS_ID,
        @Query("count") count: Int = VkHelpData.PAGE_SIZE,
        @Query("offset") page: Int,
        @Query("access_token") accessToken: String = VkHelpData.VK_API,
        @Query("v") version: String = VkHelpData.API_V
    ): Response<VkTdnewsDTO>
}