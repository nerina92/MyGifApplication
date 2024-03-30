package com.example.mygifapplication.data.repository

import com.example.mygifapplication.data.model.api.ResponseTrendingGifs
import com.example.mygifapplication.data.dataSources.ApiService
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
    private val service: ApiService
) : GifRepository {
    //val service = ApiServiceFactory.makeRetrofitService()
    override suspend fun getTrendingGifs(apiKey: String): ResponseTrendingGifs {
        return service.getTrendingGifs(apiKey)
    }

    override suspend fun searchGifs(apiKey: String, search: String): ResponseTrendingGifs {
        return service.searchGifs(apiKey, search)
    }

}