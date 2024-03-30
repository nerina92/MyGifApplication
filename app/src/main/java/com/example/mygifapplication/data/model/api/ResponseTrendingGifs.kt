package com.example.mygifapplication.data.model.api

data class ResponseTrendingGifs(
    val data: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)