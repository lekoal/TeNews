package com.private_projects.tenews.data.ferra

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.private_projects.tenews.domain.ferra.VkFerraApi

class VkFerraPagingSource(private val vkFerraApi: VkFerraApi) :
    PagingSource<Int, VkFerraDTO.Response.Item>() {
    override fun getRefreshKey(state: PagingState<Int, VkFerraDTO.Response.Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VkFerraDTO.Response.Item> {
        return try {
            val position = params.key ?: 1
            val resList = mutableListOf<VkFerraDTO.Response.Item>()
            vkFerraApi.getNews(page = position).let { resp ->
                resp.body()?.response?.items?.forEach { item ->
                    item.attachments.forEach { attachment ->
                        if (attachment.link.url.contains("https://www.ferra.ru/news/")) {
                            resList.add(item)
                        }
                    }
                }
            }

            LoadResult.Page(
                data = resList,
                prevKey = if (position == 1) null
                else position - 1,
                nextKey = position + 1
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }

}