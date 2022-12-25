package com.example.githubuserapp

data class DetailUserResponse(
	val login: String,
    val name: String,
	val id: Int,
	val avatar_url: String,
    val follower_url: String,
    val following_url: String,
    val company: String,
    val location: String,
    val followers: Int,
    val following: Int
)
