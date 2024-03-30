package com.example.mygifapplication.data.repository

import com.example.mygifapplication.data.model.db.GifDao
import com.example.mygifapplication.data.model.domain.Gif as GifDomain

class LocalDataSource (private val gifDao: GifDao){

    suspend fun isEmpty(): Boolean = gifDao.gifCount() == 0

    suspend fun getAll(): List<GifDomain> = gifDao.getAll()


}