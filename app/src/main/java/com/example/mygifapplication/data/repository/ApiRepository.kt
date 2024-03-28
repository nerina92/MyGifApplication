package com.example.mygifapplication.data.repository

import com.example.mygifapplication.data.model.ResponseTrendingGifs

interface ApiRepository {
    suspend fun getTrendingGifs(apiKey: String): ResponseTrendingGifs

    suspend fun searchGifs(apiKey: String, search: String): ResponseTrendingGifs
}