package com.example.githubuserapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<ListUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<User>>
}