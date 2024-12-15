package com.example.jadwalpengajian

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.jadwalpengajian.data.Favorite
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val scheduleList: List<Pengajian>, // List data Pengajian
    private val onFavoriteClick: (Pengajian, ImageView) -> Unit, // Callback untuk event klik favorit
    private val onRemoveFavoriteClick: (Favorite) -> Unit // Callback untuk event hapus favorit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    // Membuat ViewHolder untuk item RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    // Menghubungkan data ke ViewHolder
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val scheduleItem = scheduleList[position]
        holder.bind(scheduleItem, onFavoriteClick, onRemoveFavoriteClick)
    }

    // Menentukan jumlah item di RecyclerView
    override fun getItemCount(): Int {
        return scheduleList.size
    }

    // ViewHolder untuk item RecyclerView
    class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Menghubungkan data ke item tampilan
        fun bind(jadwalPengajian: Pengajian, onFavoriteClick: (Pengajian, ImageView) -> Unit, onRemoveFavoriteClick: (Favorite) -> Unit) {
            // Set data ke TextView
            binding.tvTitle.text = jadwalPengajian.judul
            binding.tvSpeaker.text = jadwalPengajian.pembicara
            binding.tvDate.text = jadwalPengajian.tanggal

            // Set ikon favorit sesuai status
            // Misalnya, jika Anda memiliki daftar favorit, Anda bisa memeriksa di sini
            // Untuk contoh ini, kita anggap semua jadwal pengajian adalah favorit
            // Anda perlu menyesuaikan logika ini sesuai dengan data yang Anda miliki
            binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_24) // Ikon terisi
            binding.ivFavorite.setOnClickListener {
                onFavoriteClick(jadwalPengajian, binding.ivFavorite)
            }
        }
    }
}
