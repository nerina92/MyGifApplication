package com.example.mygifapplication.data.model

data class ResponseTrendingGifs(
    val data: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)