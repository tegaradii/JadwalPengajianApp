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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data dari argumen
        val judul = arguments?.getString("judul")
        val pembicara = arguments?.getString("pembicara")
        val tanggal = arguments?.getString("tanggal")
        val waktu = arguments?.getString("waktu")
        val deskripsi = arguments?.getString("deskripsi")

        displayDetails(judul, pembicara, tanggal, waktu, deskripsi)
    }

    private fun displayDetails(judul: String?, pembicara: String?, tanggal: String?, waktu: String?, deskripsi: String?) {
        binding.tvJudul.text = judul
        binding.tvPembicara.text = pembicara
        binding.tvTanggal.text = tanggal
        binding.tvWaktu.text = waktu
        binding.tvDeskripsi.text = deskripsi
    }
}