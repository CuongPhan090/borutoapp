package com.cp.borutoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cp.borutoapp.data.model.Hero
import com.cp.borutoapp.data.model.HeroRemoteKey

@Database(entities = [Hero::class, HeroRemoteKey::class], version = 1)
@TypeConverters(value = [BorutoDataTypeConverter::class])
abstract class BorutoDatabase : RoomDatabase() {
    abstract fun getHeroDao(): HeroDao
    abstract fun getHeroRemoteKeyDao(): HeroRemoteKeyDao
}
