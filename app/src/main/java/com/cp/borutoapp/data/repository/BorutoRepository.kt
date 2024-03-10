package com.cp.borutoapp.data.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface BorutoRepository {

    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}

class BorutoRepositoryImpl @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) : BorutoRepository {

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStoreOperations.saveOnBoardingState(completed)
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStoreOperations.readOnBoardingState()
    }
}
