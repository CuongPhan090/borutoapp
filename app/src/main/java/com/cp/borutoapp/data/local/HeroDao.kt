package com.cp.borutoapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cp.borutoapp.data.model.Hero

@Dao
interface HeroDao {

    @Query("SELECT * FROM HERO_TABLE ORDER BY id ASC")
    fun getAllHeroes(): PagingSource<Int, Hero>

    @Query("SELECT * FROM HERO_TABLE WHERE id=:id")
    fun getSelectedHero(id: String): Hero

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHeroes(heroes: List<Hero>)

    @Query("DELETE FROM HERO_TABLE")
    suspend fun deleteAllHeroes()
}
