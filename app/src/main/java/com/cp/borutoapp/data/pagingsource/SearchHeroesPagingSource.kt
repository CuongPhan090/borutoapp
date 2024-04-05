package com.cp.borutoapp.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cp.borutoapp.data.remote.BorutoApi
import com.cp.borutoapp.data.remote.models.HeroResponse
import javax.inject.Inject

class SearchHeroesPagingSource @Inject constructor(
    private val borutoApi: BorutoApi,
    private val query: String
) : PagingSource<Int, HeroResponse>() {
    override fun getRefreshKey(state: PagingState<Int, HeroResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HeroResponse> {
        try {
            val apiResponse = borutoApi.searchHeroes(query)
            val heroes = apiResponse.heroes
            return if (heroes.isNotEmpty()) {
                LoadResult.Page(
                    data = heroes,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
