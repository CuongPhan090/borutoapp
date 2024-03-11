package com.cp.borutoapp.di

import android.content.Context
import androidx.room.Room
import com.cp.borutoapp.data.local.BorutoDatabase
import com.cp.borutoapp.data.remote.BorutoApi
import com.cp.borutoapp.data.repository.BorutoRepository
import com.cp.borutoapp.data.repository.BorutoRepositoryImpl
import com.cp.borutoapp.data.repository.DataStoreOperationImpl
import com.cp.borutoapp.data.repository.DataStoreOperations
import com.cp.borutoapp.util.Constant.BASE_URL
import com.cp.borutoapp.util.Constant.BORUTO_DATABASE
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
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

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit): BorutoApi {
        return retrofit.create(BorutoApi::class.java)
    }
}
