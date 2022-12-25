package com.example.githubuserapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM user_favorite")
    fun getFavorite(): LiveData<List<Favorite>>

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun removeFavorite(id: Int): Int

    @Query("SELECT COUNT(*) FROM user_favorite WHERE user_favorite.id = :id")
    fun checkFavorite(id: Int): Int

    @Insert
    fun addFavorite(favorite: Favorite)
}