package com.cp.borutoapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cp.borutoapp.data.local.entities.HeroEntity

@Dao
interface HeroDao {

    @Query("SELECT * FROM HERO_TABLE ORDER BY id ASC")
    fun getAllHeroes(): PagingSource<Int, HeroEntity>

    @Query("SELECT * FROM HERO_TABLE WHERE id=:id")
    fun getSelectedHero(id: Int): HeroEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHeroes(heroes: List<HeroEntity>)

    @Query("DELETE FROM HERO_TABLE")
    suspend fun deleteAllHeroes()
}
