package com.example.mygifapplication.data

import com.example.mygifapplication.data.model.db.Gif as DbGif
import com.example.mygifapplication.data.model.domain.Gif as GifDomain
import com.example.mygifapplication.data.model.api.Data as GifApi


fun DbGif.dbToDomain() = GifDomain(
    id = id,
    title = title,
    url = url,
    altText = altText
)

fun GifApi.toDB() = DbGif(
    id = null,
    title = title,
    url = url,
    altText = alt_text ?: title,
    importDate = import_datetime,
    dateTrending = trending_datetime
)

fun GifApi.apiToDomain() = GifDomain(
    id = null,
    title = title,
    url = images.original.url,
    altText = alt_text ?: title
)

