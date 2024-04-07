package com.cp.borutoapp.data.repository

import androidx.paging.PagingData
import com.cp.borutoapp.data.local.entities.HeroEntity
import com.cp.borutoapp.data.remote.models.HeroResponse
import com.cp.borutoapp.presentation.model.Hero
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BorutoRepository {

    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
    fun getAllHeroes(): Flow<PagingData<HeroEntity>>
    suspend fun getSelectedHeroes(heroId: String): HeroEntity
    fun searchHeroes(query: String): Flow<PagingData<HeroResponse>>
}

class BorutoRepositoryImpl @Inject constructor(
    private val dataStoreOperations: DataStoreOperations,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BorutoRepository {

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStoreOperations.saveOnBoardingState(completed)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreOperations.readOnBoardingState()
    }

    override fun getAllHeroes(): Flow<PagingData<HeroEntity>> {
        return remoteDataSource.getAllHeroes()
    }

    override suspend fun getSelectedHeroes(heroId: String): HeroEntity {
        return localDataSource.getSelectedHero(heroId = heroId)
    }

    override fun searchHeroes(query: String): Flow<PagingData<HeroResponse>> {
        return remoteDataSource.searchHeroes(query)
    }
}
