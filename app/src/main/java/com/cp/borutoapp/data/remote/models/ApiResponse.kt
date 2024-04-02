package com.cp.borutoapp.data.remote.models

import com.cp.borutoapp.data.local.entities.HeroEntity
import com.cp.borutoapp.data.local.entities.HeroRemoteKeyEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val heroes: List<HeroResponse> = emptyList(),
    val lastUpdated: Long? = null
)

@JsonClass(generateAdapter = true)
data class HeroResponse(
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

fun HeroResponse.toHeroEntity() = HeroEntity(
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

fun ApiResponse.toListOfHeroRemoteKeyEntity() =
    heroes.map {
        HeroRemoteKeyEntity(
            id = it.id,
            prevPage = this.prevPage,
            nextPage = this.nextPage,
            lastUpdated = lastUpdated
        )
    }