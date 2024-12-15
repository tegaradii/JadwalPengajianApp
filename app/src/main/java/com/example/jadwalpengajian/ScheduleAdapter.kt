package com.example.jadwalpengajian

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.jadwalpengajian.data.Favorite
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.databinding.ItemScheduleBinding

class ScheduleAdapter(
    private val scheduleList: List<Pengajian>,
    private val onFavoriteClick: (Pengajian, ImageView) -> Unit,
    private val onRemoveFavoriteClick: (Favorite) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private val favoriteStatusMap = mutableMapOf<String, Boolean>()
    private var itemClickListener: ((Pengajian) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val scheduleItem = scheduleList[position]
        holder.bind(scheduleItem, onFavoriteClick, favoriteStatusMap[scheduleItem.judul] ?: false)

        // Set listener untuk item klik
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(scheduleItem)
        }
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    fun updateFavoriteStatus(jadwal: Pengajian, isFavorite: Boolean) {
        favoriteStatusMap[jadwal.judul] = isFavorite
        notifyDataSetChanged() // Memperbarui tampilan
    }

    fun setOnItemClickListener(listener: (Pengajian) -> Unit) {
        itemClickListener = listener
    }

    class ScheduleViewHolder(private val binding: ItemScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(jadwalPengajian: Pengajian, onFavoriteClick: (Pengajian, ImageView) -> Unit, isFavorite: Boolean) {
            binding.tvTitle.text = jadwalPengajian.judul
            binding.tvSpeaker.text = jadwalPengajian.pembicara
            binding.tvDate.text = jadwalPengajian.tanggal

            // Set ikon favorit sesuai status
            binding.ivFavorite.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
            binding.ivFavorite.setOnClickListener {
                onFavoriteClick(jadwalPengajian, binding.ivFavorite)
            }
        }
    }
}


