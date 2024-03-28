package com.cp.borutoapp.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cp.borutoapp.data.local.BorutoDatabase
import com.cp.borutoapp.data.local.entities.HeroEntity
import com.cp.borutoapp.data.local.entities.HeroRemoteKeyEntity
import com.cp.borutoapp.data.remote.BorutoApi
import com.cp.borutoapp.data.remote.models.toHeroEntity
import com.cp.borutoapp.data.remote.models.toListOfHeroRemoteKeyEntity
import javax.inject.Inject

/**
 * Remote Mediator will cache the network response to local database
 */
@OptIn(ExperimentalPagingApi::class)
class HeroRemoteMediator @Inject constructor(
    private val borutoDatabase: BorutoDatabase,
    private val borutoApi: BorutoApi
) : RemoteMediator<Int, HeroEntity>() {

    private val heroDao = borutoDatabase.getHeroDao()
    private val heroRemoteKeyDao = borutoDatabase.getHeroRemoteKeyDao()

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, HeroEntity>): HeroRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                heroRemoteKeyDao.getRemoteKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, HeroEntity>): HeroRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { hero ->
            heroRemoteKeyDao.getRemoteKey(
                id = hero.id
            )
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, HeroEntity>): HeroRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { hero ->
            heroRemoteKeyDao.getRemoteKey(
                id = hero.id
            )
        }
    }

        override suspend fun load(loadType: LoadType, state: PagingState<Int, HeroEntity>): MediatorResult {
        return try {
            val page = when (loadType) {
                // init load or invalidate
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextPage
                }
            }

            val response = borutoApi.getAllHero(page = page)

            if (response.heroes.isNotEmpty()) {
                borutoDatabase.withTransaction {
                    // invalidate the data
                    if (loadType == LoadType.REFRESH) {
                        heroDao.deleteAllHeroes()
                        heroRemoteKeyDao.deleteAllRemoteKeys()
                    }

                    // caches new data from api response
                    heroDao.addHeroes(heroes = response.heroes.map { it.toHeroEntity() })
                    heroRemoteKeyDao.addAllRemoteKeys(heroRemoteKeys = response.toListOfHeroRemoteKeyEntity())
                }
            }

            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }
}