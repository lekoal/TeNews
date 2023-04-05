package com.private_projects.tenews.data.rssnews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.private_projects.tenews.utils.RssCommon
import com.private_projects.tenews.utils.RssDTOConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class RssDataSource(private val rssCommon: RssCommon) : PagingSource<Int, RssDTO>() {
    override fun getRefreshKey(state: PagingState<Int, RssDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RssDTO> {
        return withContext(Dispatchers.IO) {
            try {
                val currentPageKey = params.key ?: 1
                val rssDTOConverter = RssDTOConverter()
                val response = rssCommon.get(currentPageKey)
                val emptyDTOList = listOf(
                    RssDTO(
                        id = 1,
                        ownerDomain = "empty",
                        date = Date(),
                        title = "empty",
                        description = "empty",
                        imageUrl = "",
                        newsUrl = ""
                    )
                )
                val responseDTO = if (!response.isNullOrEmpty()) {
                    rssDTOConverter.convert(response)
                } else {
                    emptyDTOList
                }

                val prevKey = if (currentPageKey == 1) null else currentPageKey - 1
                LoadResult.Page(
                    data = responseDTO,
                    prevKey = prevKey,
                    nextKey = currentPageKey.plus(1)
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }
}