package com.example.githubuserapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val detailUser = MutableLiveData<DetailUserResponse>()

    private var favoriteDatabase: FavoriteRoomDatabase? = FavoriteRoomDatabase.getDatabase(application)
    private var favoriteDao: FavoriteDao? = favoriteDatabase?.favoriteDao()


    fun setDetailUsers(username: String){
        ApiConfig.getApiService()
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful){
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getDetailUsers() : LiveData<DetailUserResponse>{
        return detailUser
    }

    fun checkFavorite(id: Int) = favoriteDao?.checkFavorite(id)

    fun addFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val users = Favorite(
                username,
                id,
                avatarUrl
            )
            favoriteDao?.addFavorite(users)
        }
    }

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.removeFavorite(id)
        }
    }
}