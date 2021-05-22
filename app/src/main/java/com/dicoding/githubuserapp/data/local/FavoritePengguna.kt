package com.dicoding.githubuserapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_pengguna")
data class FavoritePengguna(
    val login: String,
    @PrimaryKey
    val id: Int
): Serializable
