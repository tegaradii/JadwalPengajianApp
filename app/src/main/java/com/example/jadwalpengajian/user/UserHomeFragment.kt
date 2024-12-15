package com.example.jadwalpengajian.user

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.ScheduleAdapter
import com.example.jadwalpengajian.data.AppDatabase
import com.example.jadwalpengajian.data.Favorite
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.data.network.ApiClient
import com.example.jadwalpengajian.data.network.ApiService
import com.example.jadwalpengajian.databinding.FragmentUserHomeBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserHomeFragment : Fragment(R.layout.fragment_user_home) {

    private lateinit var binding: FragmentUserHomeBinding
    private lateinit var database: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserHomeBinding.bind(view)

        // Inisialisasi database
        database = AppDatabase.getDatabase(requireContext())

        // Ambil data dari API
        fetchPengajianData()
    }

    private fun fetchPengajianData() {
        val retrofit = ApiClient.getInsance().create(ApiService::class.java)
        retrofit.getAllPengajian().enqueue(object : Callback<List<Pengajian>> {
            override fun onResponse(call: Call<List<Pengajian>>, response: Response<List<Pengajian>>) {
                if (response.isSuccessful) {
                    val pengajianList = response.body()
                    pengajianList?.let {
                        setupRecyclerView(it)
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Pengajian>>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(scheduleList: List<Pengajian>) {
        val adapter = ScheduleAdapter(scheduleList, { jadwal, favoriteIcon ->
            // Logika untuk menambahkan ke favorit
            addToFavorites(jadwal, favoriteIcon)
        }, { favorite ->
            // Logika untuk menghapus favorit (jika diperlukan)
        })

        binding.rvPengajian.adapter = adapter
        binding.rvPengajian.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun addToFavorites(jadwal: Pengajian, favoriteIcon: ImageView) {
        val favorite = Favorite(
            id = 0, // ID akan di-generate oleh Room
            judul = jadwal.judul,
            pembicara = jadwal.pembicara,
            tanggal = jadwal.tanggal,
            waktu = jadwal.waktu,
            deskripsi = jadwal.deskripsi
        )

        lifecycleScope.launch {
            // Cek apakah sudah ada di favorit
            val existingFavorites = database.favoriteDao().getAllFavorites()
            if (existingFavorites.any { it.judul == jadwal.judul }) {
                // Jika sudah ada, beri tahu pengguna
                favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24)
                Toast.makeText(requireContext(), "${jadwal.judul} sudah ada di favorit", Toast.LENGTH_SHORT).show()
            } else {
                // Tambahkan ke daftar favorit
                database.favoriteDao().insert(favorite)
                favoriteIcon.setImageResource(R.drawable.baseline_favorite_24) // Ikon favorit
                Toast.makeText(requireContext(), "${jadwal.judul} ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
