package com.cp.borutoapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cp.borutoapp.util.Constant.HERO_REMOTE_KEY_DATABASE_TABLE

@Entity(tableName = HERO_REMOTE_KEY_DATABASE_TABLE)
data class HeroRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val nextKey: Int?,
    val prevKey: Int?
)
