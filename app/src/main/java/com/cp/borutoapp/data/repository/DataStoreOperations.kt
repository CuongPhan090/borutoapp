package com.cp.borutoapp.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.cp.borutoapp.util.Constant.DATA_STORE_OPERATIONS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.io.IOException

interface DataStoreOperations {

    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_OPERATIONS)

class DataStoreOperationImpl(private val context: Context) : DataStoreOperations {

    companion object {
        val ON_BOARDING_STATE = booleanPreferencesKey("on_boarding_state")
    }

    override suspend fun saveOnBoardingState(completed: Boolean) {
        try {
            context.dataStore.edit { dataStore ->
                dataStore[ON_BOARDING_STATE] = completed
            }
        } catch (ioException: IOException) {
            Log.e("Boruto", "Unable to save on boarding state")
        } catch (exception: Exception) {
            Log.e("Boruto", "Exception in transform block")
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return try {
            context.dataStore.data.map { dataStore ->
                dataStore[ON_BOARDING_STATE] ?: false
            }
        } catch (exception: Exception) {
            Log.e("Boruto", "Unable to retrieve the value, key not found")
            flowOf(false)
        }
    }
}