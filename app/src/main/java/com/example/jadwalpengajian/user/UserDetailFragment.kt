package com.example.jadwalpengajian.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private lateinit var binding: FragmentUserDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menghubungkan binding
        binding = FragmentUserDetailBinding.bind(view)

        // Mendapatkan data dari arguments (jika ada)
        val eventTitle = arguments?.getString("eventTitle") ?: "Penguatan Iman di Era Digital"
        val speaker = arguments?.getString("speaker") ?: "Ust. H. Ahmad Syaifullah, M.A."
        val date = arguments?.getString("date") ?: "20 Desember 2024"
        val time = arguments?.getString("time") ?: "19.00 - 21.00 WIB"
        val description = arguments?.getString("description") ?: "Deskripsi tidak tersedia."

        // Mengisi data dinamis ke tampilan
        binding.tvHeader.text = "DETAIL PENGAJIAN\nMASJID AL-FALAH"
        binding.tvJudul.text = eventTitle
        binding.tvPembicara.text = speaker
        binding.tvTanggal.text = date
        binding.tvWaktu.text = time
        binding.tvDeskripsi.text = description

        // Menampilkan gambar banner (ganti dengan gambar dinamis jika diperlukan)
        binding.imgBanner.setImageResource(R.drawable.banner_pengajian) // Ubah jika gambar dinamis
    }

    companion object {
        // Fungsi untuk membuat instance fragment dengan data dinamis
        fun newInstance(eventTitle: String, speaker: String, date: String, time: String, description: String): UserDetailFragment {
            val fragment = UserDetailFragment()
            val bundle = Bundle().apply {
                putString("eventTitle", eventTitle)
                putString("speaker", speaker)
                putString("date", date)
                putString("time", time)
                putString("description", description)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
