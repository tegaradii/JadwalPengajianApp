package com.example.jadwalpengajian.user

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBookmarkBinding.bind(view)

        // Inisialisasi database
        database = AppDatabase.getDatabase(requireContext())

        // Ambil data favorit dari database
        lifecycleScope.launch {
            val favorites = database.favoriteDao().getAllFavorites()
            setupRecyclerView(favorites)
        }
    }

    private fun setupRecyclerView(favorites: List<Favorite>) {
        // Mengonversi daftar favorit menjadi daftar pengajian
        val pengajianList = favorites.map {
            Pengajian(
                _id = null, // Jika Anda tidak memiliki ID dari Favorite, Anda bisa mengatur ini ke null
                judul = it.judul,
                pembicara = it.pembicara,
                tanggal = it.tanggal,
                waktu = it.waktu,
                deskripsi = it.deskripsi
            )
        }

        val adapter = ScheduleAdapter(pengajianList, { jadwal, favoriteIcon ->
            // Logika untuk menghapus favorit
            removeFromFavorites(jadwal)
        }, { favorite ->
            // Tidak ada logika untuk menambahkan favorit di sini
        })

        binding.rvFavoritesUser.adapter = adapter
        binding.rvFavoritesUser.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun removeFromFavorites(jadwal: Pengajian) {
        val favoriteToRemove = Favorite(0, jadwal.judul, jadwal.pembicara, jadwal.tanggal, jadwal.waktu, jadwal.deskripsi)

        lifecycleScope.launch {
            database.favoriteDao().delete(favoriteToRemove) // Hapus dari database
            // Refresh daftar favorit setelah penghapusan
            val updatedFavorites = database.favoriteDao().getAllFavorites()
            setupRecyclerView(updatedFavorites) // Memperbarui RecyclerView
        }
    }
}
