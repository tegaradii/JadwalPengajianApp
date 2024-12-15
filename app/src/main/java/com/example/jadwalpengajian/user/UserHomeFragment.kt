package com.example.jadwalpengajian.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
        Log.d("API_DEBUG", "Mulai fetch data")

        retrofit.getAllPengajian().enqueue(object : Callback<List<Pengajian>> {
            override fun onResponse(call: Call<List<Pengajian>>, response: Response<List<Pengajian>>) {
                Log.d("API_DEBUG", "Response code: ${response.code()}")
                Log.d("API_DEBUG", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val pengajianList = response.body()
                    pengajianList?.let {
                        Log.d("API_DEBUG", "Data berhasil: ${it.size} item")
                        setupRecyclerView(it)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_DEBUG", "Error body: $errorBody")
                    Toast.makeText(requireContext(),
                        "Gagal mengambil data. Code: ${response.code()}",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Pengajian>>, t: Throwable) {
                Log.e("API_DEBUG", "Network Error: ${t.message}")
                t.printStackTrace()
                Toast.makeText(requireContext(),
                    "Gagal mengambil data: ${t.message}",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(scheduleList: List<Pengajian>) {
        lifecycleScope.launch {
            // Ambil daftar favorit dari database
            val favorites = database.favoriteDao().getAllFavorites().map { it.judul }

            val adapter = ScheduleAdapter(scheduleList, { jadwal, favoriteIcon ->
                addToFavorites(jadwal, favoriteIcon)
            }, { favorite ->
                removeFromFavorites(favorite)
            })

            // Set listener untuk item klik
            adapter.setOnItemClickListener { jadwal ->
                val bundle = Bundle().apply {
                    putString("judul", jadwal.judul)
                    putString("pembicara", jadwal.pembicara)
                    putString("tanggal", jadwal.tanggal)
                    putString("waktu", jadwal.waktu)
                    putString("deskripsi", jadwal.deskripsi)
                }
                findNavController().navigate(R.id.action_userHomeFragment_to_userDetailFragment, bundle)
            }

            // Update ikon favorit berdasarkan status favorit
            scheduleList.forEach { jadwal ->
                if (favorites.contains(jadwal.judul)) {
                    adapter.updateFavoriteStatus(jadwal, true)
                } else {
                    adapter.updateFavoriteStatus(jadwal, false)
                }
            }

            binding.rvPengajian.adapter = adapter
            binding.rvPengajian.layoutManager = LinearLayoutManager(requireContext())
        }
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

    private fun removeFromFavorites(favorite: Favorite) {
        lifecycleScope.launch {
            database.favoriteDao().delete(favorite) // Hapus dari database
            Toast.makeText(requireContext(), "${favorite.judul} dihapus dari favorit", Toast.LENGTH_SHORT).show()
        }
    }
}
