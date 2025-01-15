package com.example.guide.network

import com.example.guide.AppConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://maps.googleapis.com"

val json = Json { ignoreUnknownKeys = true } // Configure the JSON instance


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        json.asConverterFactory("application/json".toMediaType()))
    .build()

interface GuideApiService {
    @GET("maps/api/place/textsearch/json")
    suspend fun findPlaceFromText(
        @Query("query") query: String,
        @Query("key") apiKey: String = AppConfig.API_KEY
    ): Response<PlacesResponse>
}

object PlacesApi {
    val retrofitService : GuideApiService by lazy {
        retrofit.create(GuideApiService::class.java)
    }
}