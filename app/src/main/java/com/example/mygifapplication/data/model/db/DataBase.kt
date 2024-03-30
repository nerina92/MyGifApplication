package com.example.mygifapplication.data.model.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Gif::class], version = 1)
abstract class DataBase : RoomDatabase() {
    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            DataBase::class.java,
            "myGifAplicattion-db"
        ).build()
    }

    abstract fun gifDao(): GifDao
}