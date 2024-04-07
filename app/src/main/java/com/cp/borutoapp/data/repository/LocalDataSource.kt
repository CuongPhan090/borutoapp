package com.cp.borutoapp.data.repository

import com.cp.borutoapp.data.local.BorutoDatabase
import com.cp.borutoapp.data.local.entities.HeroEntity

interface LocalDataSource {
    suspend fun getSelectedHero(heroId: String): HeroEntity
}

class LocalDataSourceImpl(
    private val borutoDatabase: BorutoDatabase
) : LocalDataSource {
    override suspend fun getSelectedHero(heroId: String): HeroEntity {
        return borutoDatabase.getHeroDao().getSelectedHero(id = heroId)
    }
}