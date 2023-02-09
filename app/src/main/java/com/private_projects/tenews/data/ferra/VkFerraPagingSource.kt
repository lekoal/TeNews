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
            val response = vkFerraApi.getNews(
                page = position
            )
            LoadResult.Page(
                data = response.body()!!.response.items,
                prevKey = if (position == 1) null
                else position - 1,
                nextKey = position + 1
            )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }

}