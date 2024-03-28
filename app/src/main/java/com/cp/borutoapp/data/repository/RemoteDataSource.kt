package com.cp.borutoapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cp.borutoapp.data.local.BorutoDatabase
import com.cp.borutoapp.data.local.entities.HeroEntity
import com.cp.borutoapp.data.pagingsource.HeroRemoteMediator
import com.cp.borutoapp.data.remote.BorutoApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RemoteDataSource {
    fun getAllHeroes(): Flow<PagingData<HeroEntity>>
    fun searchHeroes(): Flow<PagingData<HeroEntity>>
}

@OptIn(ExperimentalPagingApi::class)
class RemoteDataSourceImpl @Inject constructor(
    private val borutoDatabase: BorutoDatabase,
    private val borutoApi: BorutoApi
) : RemoteDataSource {

    override fun getAllHeroes(): Flow<PagingData<HeroEntity>> {
        val pagingSourceFactory = { borutoDatabase.getHeroDao().getAllHeroes() }
        return Pager(
            config = PagingConfig(pageSize = 3),
            remoteMediator = HeroRemoteMediator(
                borutoDatabase = borutoDatabase,
                borutoApi = borutoApi
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchHeroes(): Flow<PagingData<HeroEntity>> {
        TODO("Not yet implemented")
    }
}