package com.cp.borutoapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cp.borutoapp.presentation.model.Hero
import com.cp.borutoapp.util.Constant.HERO_DATABASE_TABLE

@Entity(tableName = HERO_DATABASE_TABLE)
data class HeroEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val image: String,
    val about: String,
    val rating: Double,
    val power: Int,
    val month: String,
    val day: String,
    val family: List<String>,
    val abilities: List<String>,
    val natureTypes: List<String>
)

fun HeroEntity.toHero() = Hero(
    id = id,
    name = name,
    image = image,
    about = about,
    rating = rating,
    power = power,
    month = month,
    day = day,
    family = family,
    abilities = abilities,
    natureTypes = natureTypes
)