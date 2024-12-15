package com.example.jadwalpengajian.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.ScheduleAdapter
import com.example.jadwalpengajian.data.AppDatabase
import com.example.jadwalpengajian.data.Favorite
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.databinding.FragmentUserBookmarkBinding
import kotlinx.coroutines.launch

class UserBookmarkFragment : Fragment(R.layout.fragment_user_bookmark) {

    private lateinit var binding: FragmentUserBookmarkBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: ScheduleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBookmarkBinding.bind(view)

        // Inisialisasi database
        database = AppDatabase.getDatabase(requireContext())

        // Ambil data favorit dari database
        loadFavorites()
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            val favorites = database.favoriteDao().getAllFavorites()
            setupRecyclerView(favorites)
        }
    }

    private fun setupRecyclerView(favorites: List<Favorite>) {
        val pengajianList = favorites.map {
            Pengajian(
                _id = null,
                judul = it.judul,
                pembicara = it.pembicara,
                tanggal = it.tanggal,
                waktu = it.waktu,
                deskripsi = it.deskripsi
            )
        }

        adapter = ScheduleAdapter(pengajianList, { jadwal, favoriteIcon ->
            removeFromFavorites(jadwal)
        }, { favorite ->
            // Tidak ada logika untuk menambahkan favorit di sini
        })

        binding.rvFavoritesUser.adapter = adapter
        binding.rvFavoritesUser.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun removeFromFavorites(jadwal: Pengajian) {
        lifecycleScope.launch {
            // Ambil ID dari favorit yang ingin dihapus
            val favoriteToRemove = database.favoriteDao().getAllFavorites().find { it.judul == jadwal.judul }
            favoriteToRemove?.let {
                database.favoriteDao().delete(it) // Hapus dari database
                Toast.makeText(requireContext(), "${jadwal.judul} dihapus dari favorit", Toast.LENGTH_SHORT).show()
                loadFavorites() // Memperbarui daftar favorit setelah penghapusan
            } ?: run {
                Toast.makeText(requireContext(), "Favorit tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




