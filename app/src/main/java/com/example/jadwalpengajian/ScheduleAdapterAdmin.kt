package com.example.jadwalpengajian

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.databinding.ItemScheduleAdminBinding
import com.example.jadwalpengajian.databinding.ItemScheduleBinding


class ScheduleAdapterAdmin( private val scheduleList: List<Pengajian>,
                            private val onEditClick: (Pengajian) -> Unit,
                            private val onDeleteClick: (Pengajian) -> Unit,
                            private val onDetailClick: (Pengajian) -> Unit) :
    RecyclerView.Adapter<ScheduleAdapterAdmin.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding, onEditClick, onDeleteClick, onDetailClick)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val scheduleItem = scheduleList[position]
        holder.bind(scheduleItem)
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    class ScheduleViewHolder(private val binding: ItemScheduleAdminBinding,
                             private val onEditClick: (Pengajian) -> Unit,
                             private val onDeleteClick: (Pengajian) -> Unit,
                             private val onDetailClick: (Pengajian) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(jadwalPengajian: Pengajian) {
            binding.tvTitle.text = jadwalPengajian.judul
            binding.tvSpeaker.text = jadwalPengajian.pembicara
            binding.tvDate.text = jadwalPengajian.tanggal
            binding.btnEdit.setOnClickListener {
                onEditClick(jadwalPengajian)
            }

            binding.btnHapus.setOnClickListener {
                onDeleteClick(jadwalPengajian)
            }
            binding.btnDetail.setOnClickListener {
                onDetailClick(jadwalPengajian)
            }

        }
    }
}
