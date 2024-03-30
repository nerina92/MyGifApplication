package com.example.mygifapplication.di

import com.example.mygifapplication.data.repository.GifRepository
import com.example.mygifapplication.data.repository.GifRepositoryImpl
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
    fun apiRepository(repo: GifRepositoryImpl): GifRepository {
        return repo
    }
}