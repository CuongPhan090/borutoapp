package com.cp.borutoapp.di

import android.content.Context
import androidx.room.Room
import com.cp.borutoapp.data.local.BorutoDatabase
import com.cp.borutoapp.data.repository.BorutoRepository
import com.cp.borutoapp.data.repository.BorutoRepositoryImpl
import com.cp.borutoapp.data.repository.DataStoreOperationImpl
import com.cp.borutoapp.data.repository.DataStoreOperations
import com.cp.borutoapp.util.Constant.BORUTO_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BorutoModule {

    @Provides
    @Singleton
    fun provideBorutoDatabase(@ApplicationContext context: Context): BorutoDatabase {
        return Room.databaseBuilder(context, BorutoDatabase::class.java, BORUTO_DATABASE).build()
    }

    @Provides
    @Singleton
    fun provideDataStoreOperation(@ApplicationContext context: Context): DataStoreOperations {
        return DataStoreOperationImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideBorutoRepository(dataStoreOperationsImpl: DataStoreOperations): BorutoRepository {
        return BorutoRepositoryImpl(dataStoreOperations = dataStoreOperationsImpl)
    }
}
