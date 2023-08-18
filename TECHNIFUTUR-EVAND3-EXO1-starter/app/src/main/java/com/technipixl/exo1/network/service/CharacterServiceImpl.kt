package com.technipixl.exo1.network.service

import com.technipixl.exo1.network.model.CharacterResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class CharacterServiceImpl {
        fun getRetrofit(): Retrofit {
            val okBuilder = OkHttpClient().newBuilder().apply {
                connectTimeout(60, TimeUnit.SECONDS)
                callTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
            }

            return Retrofit.Builder()
                .baseUrl("https://gateway.marvel.com/v1/") //url du JSON de l'API
                .addConverterFactory(GsonConverterFactory.create())
                .client(okBuilder.build())
                .build()
        }
        suspend fun getCharacterList(
            apiKey: String,
            timeStamp: Long,
            hash: String?,
            limit: Int
        ): Response<CharacterResponse> =
            getRetrofit().create(CharacterService::class.java).getCharacterList(apiKey, timeStamp, hash, limit
            )

}