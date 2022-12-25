package com.example.githubuserapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "user_favorite")
data class Favorite (
    val login: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val avatar_url: String
): Serializable