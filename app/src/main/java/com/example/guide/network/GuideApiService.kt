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

object placesApi {
    val retrofitService : GuideApiService by lazy {
        retrofit.create(GuideApiService::class.java)
    }
}

interface GuideApiService {
    @GET("maps/api/place/findplacefromtext/json")
    fun findPlaceFromText(
        @Query("input") input: String,
        @Query("inputtype") inputType: String = "textquery",
        @Query("fields") fields: String = "formatted_address,name,photo",
        @Query("key") apiKey: String = "GOOGLE_MAPS_API_KEY"
    ): Call<PlacesResponse>
}