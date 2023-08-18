package com.technipixl.exo1.network.service

import com.technipixl.exo1.network.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CharacterService {
    @Headers("Content-type: application/json")
    @GET("public/characters")
    suspend fun getCharacterList(
        @Query("apikey", encoded = true) api: String,
        @Query("ts", encoded = false) ts: Long,
        @Query("hash", encoded = false) hash: String?,
        @Query("limit", encoded = false) limite: Int
    ): Response<CharacterResponse>
}