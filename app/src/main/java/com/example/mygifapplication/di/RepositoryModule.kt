package com.example.mygifapplication.di

import com.example.mygifapplication.data.repository.ApiRepository
import com.example.mygifapplication.data.repository.ApiRepositoryImpl
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
    fun apiRepository(repo: ApiRepositoryImpl): ApiRepository {
        return repo
    }
}