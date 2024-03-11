package com.cp.borutoapp.data.remote

import com.cp.borutoapp.data.remote.models.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BorutoApi {
    @GET("/boruto/heroes")
    suspend fun getAllHero(@Query("page") page: Int = 1): ApiResponse

    @GET("/boruto/heroes/search")
    suspend fun searchHeroes(@Query("name") name: String): ApiResponse
}
