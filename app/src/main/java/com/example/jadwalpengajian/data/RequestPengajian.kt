package com.example.jadwalpengajian.data

import com.google.gson.annotations.SerializedName

data class RequestPengajian(
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
