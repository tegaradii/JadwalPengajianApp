package com.example.jadwalpengajian.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Pengajian(
    @SerializedName("_id")
    val _id: String ? = null,
    @SerializedName("judul")
    val judul: String,
    @SerializedName("pembicara")
    val pembicara: String,
    @SerializedName("tanggal")
    val tanggal: String,
    @SerializedName("waktu")
    val waktu: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
)

