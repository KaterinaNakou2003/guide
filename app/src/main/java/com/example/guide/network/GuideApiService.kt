package com.example.guide.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://maps.googleapis.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface GuideApiService {
    @GET("maps/api/place/findplacefromtext/json")
    suspend fun findPlaceFromText(
        @Query("input") input: String,
        @Query("inputtype") inputType: String = "textquery",
        @Query("fields") fields: String = "place_id,formatted_address,name,photo,rating",
        @Query("key") apiKey: String = "Insert-your-key-here"
    ): Call<PlacesResponse>
}

object PlacesApi {
    val retrofitService : GuideApiService by lazy {
        retrofit.create(GuideApiService::class.java)
    }
}