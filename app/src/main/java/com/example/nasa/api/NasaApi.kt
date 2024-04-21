package com.example.nasa.api

import com.example.nasa.model.NasaData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NasaApi {

    @Headers("DEMO_KEY: $api_key")
    @GET("/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY")
    suspend fun photo(@Query("page") page: Int): NasaData

    private companion object {
        private const val api_key = "f7PxyvdEQas5zRwCJcBiazmQ7lFCywhTNSWtUDIy"
    }
}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://api.nasa.gov/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(NasaApi::class.java)

