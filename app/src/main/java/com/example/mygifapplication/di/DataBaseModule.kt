package com.example.mygifapplication.di

import android.content.Context
import androidx.room.Room
import com.example.mygifapplication.data.model.db.DataBase
import com.example.mygifapplication.data.model.db.GifDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            "myGifAplicattion-db"
        ).build()
    }

    @Provides
    fun provideGifDao(appDatabase: DataBase): GifDao {
        return appDatabase.gifDao()
    }
}