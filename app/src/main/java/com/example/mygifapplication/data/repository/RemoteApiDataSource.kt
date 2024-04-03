package com.example.mygifapplication.data.repository

import com.example.mygifapplication.data.dataSources.ApiService
import com.example.mygifapplication.data.model.api.Data
import javax.inject.Inject

class RemoteApiDataSource @Inject constructor(
    private val service: ApiService
) {
    suspend fun getTrendingGifs(apiKey: String):List<Data> {
        return service.getTrendingGifs(apiKey).data
    }

    suspend fun searchGifs(apiKey: String, search: String): List<Data> {
        return service.searchGifs(apiKey, search).data
    }

}