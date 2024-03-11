package com.cp.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cp.borutoapp.data.local.dao.HeroDao
import com.cp.borutoapp.data.local.dao.HeroRemoteKeyDao
import com.cp.borutoapp.data.local.entities.HeroEntity
import com.cp.borutoapp.data.local.entities.HeroRemoteKeyEntity

@Database(entities = [HeroEntity::class, HeroRemoteKeyEntity::class], version = 1)
@TypeConverters(value = [BorutoDataTypeConverter::class])
abstract class BorutoDatabase : RoomDatabase() {
    abstract fun getHeroDao(): HeroDao
    abstract fun getHeroRemoteKeyDao(): HeroRemoteKeyDao
}
