package com.example.jadwalpengajian.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.data.network.ApiClient
import com.example.jadwalpengajian.data.network.ApiService
import com.example.jadwalpengajian.databinding.FragmentAdminEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminEditFragment : Fragment(R.layout.fragment_admin_edit) {
    private lateinit var binding: FragmentAdminEditBinding
    private var pengajianId: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminEditBinding.bind(view)

        // Ambil ID pengajian dari arguments
        pengajianId = arguments?.getString("pengajianId") ?: ""
        Log.d("AdminEditFragment", "Received pengajianId: $pengajianId")

        // Validasi ID
        if (pengajianId.isEmpty()) {
            Toast.makeText(requireContext(), "ID Pengajian tidak ditemukan", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return
        }

        // Setup UI
        setupButtons()
        loadPengajianData()
    }

    private fun setupButtons() {
        // Button Simpan
        binding.btnSimpan.setOnClickListener {
            updatePengajian()
        }

        // Button Kembali
        binding.btnKembali.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadPengajianData() {
        Log.d("AdminEditFragment", "Loading data for pengajianId: $pengajianId")

        val retrofit = ApiClient.getInsance().create(ApiService::class.java)
        retrofit.getPengajianById(pengajianId).enqueue(object : Callback<Pengajian> {
            override fun onResponse(call: Call<Pengajian>, response: Response<Pengajian>) {
                Log.d("AdminEditFragment", "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    val pengajian = response.body()
                    Log.d("AdminEditFragment", "Pengajian data: $pengajian")

                    pengajian?.let {
                        // Isi form dengan data yang ada
                        binding.etJudulPengajian.setText(it.judul)
                        binding.etPembicara.setText(it.pembicara)
                        binding.etTanggal.setText(it.tanggal)
                        binding.etWaktu.setText(it.waktu)
                        binding.etDeskripsi.setText(it.deskripsi)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AdminEditFragment", "Error response: $errorBody")
                    Toast.makeText(
                        requireContext(),
                        "Gagal mengambil data: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Pengajian>, t: Throwable) {
                Log.e("AdminEditFragment", "API call failed", t)
                Toast.makeText(
                    requireContext(),
                    "Gagal mengambil data: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updatePengajian() {
        // Ambil nilai dari form
        val judul = binding.etJudulPengajian.text.toString()
        val pembicara = binding.etPembicara.text.toString()
        val tanggal = binding.etTanggal.text.toString()
        val waktu = binding.etWaktu.text.toString()
        val deskripsi = binding.etDeskripsi.text.toString()

        // Validasi input
        if (judul.isEmpty() || pembicara.isEmpty() || tanggal.isEmpty() ||
            waktu.isEmpty() || deskripsi.isEmpty()) {
            Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        // Buat objek Pengajian
        val pengajian = Pengajian(
            _id = pengajianId,
            judul = judul,
            pembicara = pembicara,
            tanggal = tanggal,
            waktu = waktu,
            deskripsi = deskripsi
        )

        // Kirim update ke API
        val retrofit = ApiClient.getInsance().create(ApiService::class.java)
        retrofit.updatePengajian(pengajianId, pengajian).enqueue(object : Callback<Pengajian> {
            override fun onResponse(call: Call<Pengajian>, response: Response<Pengajian>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Berhasil mengupdate data", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AdminEditFragment", "Update failed: $errorBody")
                    Toast.makeText(requireContext(), "Gagal mengupdate data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Pengajian>, t: Throwable) {
                Log.e("AdminEditFragment", "Update failed", t)
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}