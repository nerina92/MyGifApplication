package com.example.mygifapplication.data.dataSources

import com.example.mygifapplication.data.model.api.ResponseTrendingGifs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //URL BASE: https://api.giphy.com/
    @GET("/v1/gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
    ): ResponseTrendingGifs

    @GET("/v1/gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") search: String,
    ): ResponseTrendingGifs


}

object ApiServiceFactory{
    fun makeRetrofitService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.giphy.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
}