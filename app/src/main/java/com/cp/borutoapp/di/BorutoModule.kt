package com.cp.borutoapp.di

import android.content.Context
import androidx.room.Room
import com.cp.borutoapp.data.local.BorutoDatabase
import com.cp.borutoapp.data.local.HeroDao
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
}
