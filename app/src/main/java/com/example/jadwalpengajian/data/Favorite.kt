package com.example.jadwalpengajian.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite-pengajian")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val judul: String,
    val pembicara: String,
    val tanggal: String,
    val waktu: String,
    val deskripsi: String,
    var isFavorite: Boolean = false
)
