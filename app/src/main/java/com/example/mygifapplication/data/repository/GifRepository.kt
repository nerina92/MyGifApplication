package com.example.mygifapplication.data.repository

import android.content.Context
import com.example.mygifapplication.data.model.api.ResponseTrendingGifs
import com.example.mygifapplication.data.model.domain.Gif

interface GifRepository {
    suspend fun getTrendingGifs(apiKey: String, context: Context): List<Gif>

    suspend fun searchGifs(apiKey: String, search: String, context: Context): List<Gif>
}