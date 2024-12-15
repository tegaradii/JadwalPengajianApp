package com.example.jadwalpengajian.data.network


import com.example.jadwalpengajian.data.Pengajian
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("pengajian")
    fun getAllPengajian(): Call<List<Pengajian>>
    @GET("pengajian/{id}")
    fun getPengajianById(@Path("id") id: String): Call<Pengajian>
    @POST("pengajian")
    fun createPengajian(@Body pengajian: Pengajian): Call<Void>
    @POST("pengajian/{id}")
    fun updatePengajian(
        @Path("id") id: String,
        @Body pengajian: Pengajian
    ): Call<Pengajian>
    @DELETE("pengajian/{id}")
    fun deletePengajian(@Path("id") id: String): Call<Void>
}