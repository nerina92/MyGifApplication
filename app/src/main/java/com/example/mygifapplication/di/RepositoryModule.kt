package com.example.mygifapplication.di

import com.example.mygifapplication.data.dataSources.ApiService
import com.example.mygifapplication.data.model.db.GifDao
import com.example.mygifapplication.data.repository.GifRepository
import com.example.mygifapplication.data.repository.GifRepositoryImpl
import com.example.mygifapplication.data.repository.LocalDataSource
import com.example.mygifapplication.data.repository.RemoteApiDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 class RepositoryModule {

    @Singleton
    @Provides
    fun gifRepository(repo: GifRepositoryImpl): GifRepository {
        return repo
    }

    @Singleton
    @Provides
    fun localDataSource(gifDao: GifDao): LocalDataSource {
        return LocalDataSource(gifDao)
    }

    @Singleton
    @Provides
    fun remoteDataSource(service: ApiService): RemoteApiDataSource {
        return RemoteApiDataSource(service)
    }
}