package com.cp.borutoapp.data.repository

import androidx.paging.PagingData
import com.cp.borutoapp.data.local.entities.HeroEntity
import com.cp.borutoapp.presentation.model.Hero
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BorutoRepository {

    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
    fun getAllHeroes(): Flow<PagingData<HeroEntity>>
}

class BorutoRepositoryImpl @Inject constructor(
    private val dataStoreOperations: DataStoreOperations,
    private val remoteDataSource: RemoteDataSource
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
}
