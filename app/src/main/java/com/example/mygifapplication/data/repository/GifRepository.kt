package com.example.mygifapplication.data.repository

import com.example.mygifapplication.data.model.api.ResponseTrendingGifs

interface GifRepository {
    suspend fun getTrendingGifs(apiKey: String): ResponseTrendingGifs

    suspend fun searchGifs(apiKey: String, search: String): ResponseTrendingGifs
}