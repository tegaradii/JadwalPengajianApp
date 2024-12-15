package com.example.jadwalpengajian.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.data.RequestPengajian
import com.example.jadwalpengajian.data.network.ApiClient
import com.example.jadwalpengajian.data.network.ApiService
import com.example.jadwalpengajian.databinding.FragmentAdminCreateBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminCreateFragment : Fragment(R.layout.fragment_admin_create) {

    private lateinit var binding: FragmentAdminCreateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminCreateBinding.bind(view)

        binding.btnSimpan.setOnClickListener {
            Log.d("AdminCreateFragment", "Tombol Simpan diklik")

            val judulPengajian = binding.etJudulPengajian.text.toString()
            val pembicara = binding.etPembicara.text.toString()
            val tanggal = binding.etTanggal.text.toString()
            val waktu = binding.etWaktu.text.toString()
            val deskripsi = binding.etDeskripsi.text.toString()

            // Validasi input
            if (judulPengajian.isEmpty() || pembicara.isEmpty() || tanggal.isEmpty() || waktu.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = "" // Atur ID sesuai kebutuhan
            val pengajian = RequestPengajian(judulPengajian, pembicara, tanggal, waktu, deskripsi)
            val retrofit = ApiClient.getInsance().create(ApiService::class.java)

            Log.d("AdminCreateFragment", "Mengirim data: $pengajian")

            retrofit.createPengajian(pengajian).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("AdminCreateFragment", "Response: $response")
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Jadwal Pengajian berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(requireContext(), "Gagal menambahkan jadwal pengajian: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("AdminCreateFragment", "Error: ${t.message}")
                    Toast.makeText(requireContext(), "Gagal menambahkan jadwal pengajian", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
