package com.example.githubuserapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var favoriteDatabase: FavoriteRoomDatabase? = FavoriteRoomDatabase.getDatabase(application)
    private var favoriteDao: FavoriteDao? = favoriteDatabase?.favoriteDao()

    fun getFavorite(): LiveData<List<Favorite>>? {
        return favoriteDao?.getFavorite()
    }
}