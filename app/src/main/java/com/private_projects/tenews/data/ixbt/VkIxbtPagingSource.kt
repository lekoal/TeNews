package com.private_projects.tenews.data.ixbt

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.private_projects.tenews.domain.ixbt.VkIxbtApi

class VkIxbtPagingSource(private val vkIxbtApi: VkIxbtApi) :
    PagingSource<Int, VkIxbtDTO.Response.Item>() {
    override fun getRefreshKey(state: PagingState<Int, VkIxbtDTO.Response.Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VkIxbtDTO.Response.Item> {
        return try {
            val position = params.key ?: 1
            val resList = mutableListOf<VkIxbtDTO.Response.Item>()
            vkIxbtApi.getNews(page = position).let { resp ->
                resp.body()?.response?.items?.forEach { item ->
                    item.attachments.forEach { attachment ->
                        if (attachment.link?.url?.contains("https://www.ixbt.com/news/") == true) {
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