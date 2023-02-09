package com.private_projects.tenews.data.allnews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.private_projects.tenews.utils.VkDataToAllNewsDTOConverter

class VkAllNewsPagingSource(
    private val dataConverter: VkDataToAllNewsDTOConverter
) : PagingSource<Int, VkAllNewsDTO>() {
    override fun getRefreshKey(state: PagingState<Int, VkAllNewsDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VkAllNewsDTO> {
        return try {
            val position = params.key ?: 1
            val response = dataConverter.convert(
                page = position
            )
            LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null
                else position - 1,
                nextKey = position + 1
                )
        } catch (e: java.lang.Exception) {
            LoadResult.Error(e)
        }
    }

}