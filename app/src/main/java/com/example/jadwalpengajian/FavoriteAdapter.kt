package com.example.jadwalpengajian

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.databinding.ItemScheduleBinding

class FavoriteAdapter(
    private val favoriteList: List<Pengajian>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteItem = favoriteList[position]
        holder.bind(favoriteItem)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    class FavoriteViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(jadwalPengajian: Pengajian) {
            binding.tvTitle.text = jadwalPengajian.judul
            binding.tvSpeaker.text = jadwalPengajian.pembicara
            binding.tvDate.text = jadwalPengajian.tanggal

            // Disable the favorite button in the Bookmark menu
            binding.ivFavorite.setImageResource(R.drawable.baseline_favorite_24)
            binding.ivFavorite.isClickable = false
        }
    }
}
