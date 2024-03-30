package com.example.mygifapplication.data.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GifDao {
    @Query("SELECT * FROM gif")
    fun getAll(): List<Gif>

    @Query("SELECT * FROM gif WHERE id = :id")
    fun findById(id: Int): Gif

    @Query("SELECT COUNT(id) FROM gif")
    fun gifCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGifs(gifs: List<Gif>)

    @Update
    fun updateGif(gif: Gif)

}