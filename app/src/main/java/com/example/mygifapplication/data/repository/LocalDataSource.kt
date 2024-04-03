package com.example.mygifapplication.data.repository

import com.example.mygifapplication.data.dbToDomain
import com.example.mygifapplication.data.model.db.GifDao
import com.example.mygifapplication.data.model.domain.Gif as GifDomain
import com.example.mygifapplication.data.model.api.Data as GifApi
import com.example.mygifapplication.data.toDB
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val gifDao: GifDao){

    suspend fun isEmpty(): Boolean {
        val count = gifDao.gifCount()
        println("Cantidad de items en la db: $count")
        return count == 0
    }
    suspend fun getAll(): List<GifDomain> = (gifDao.getAll()).map { it.dbToDomain() }

    suspend fun getBySearch(search:String): List<GifDomain> = (gifDao.findByTitle(search)).map {it.dbToDomain()}

    suspend fun save(listGif: List<GifApi>){
        gifDao.insertGifs(listGif.map { it.toDB() })
    }

    suspend fun getByDateTrending(dateTrending: String): List<GifDomain> = (gifDao.findByDateTrending(dateTrending)).map {it.dbToDomain()}


}