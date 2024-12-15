package com.example.jadwalpengajian.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.data.network.ApiClient
import com.example.jadwalpengajian.data.network.ApiService
import com.example.jadwalpengajian.databinding.FragmentAdminDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminDetailFragment : Fragment(R.layout.fragment_admin_detail) {

    private lateinit var binding: FragmentAdminDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminDetailBinding.bind(view)

        val pengajianId = arguments?.getString("pengajianId")
        pengajianId?.let { loadPengajianDetail(it) }
    }

    private fun loadPengajianDetail(id: String) {
        val retrofit = ApiClient.getInsance().create(ApiService::class.java)
        retrofit.getPengajianById(id).enqueue(object : Callback<Pengajian> {
            override fun onResponse(call: Call<Pengajian>, response: Response<Pengajian>) {
                if (response.isSuccessful) {
                    val pengajian = response.body()
                    binding.tvJudul.text = pengajian?.judul
                    binding.tvPembicara.text = pengajian?.pembicara
                    binding.tvDeskripsi.text = pengajian?.deskripsi
                    binding.tvWaktu.text = pengajian?.waktu
                    binding.tvTanggal.text = pengajian?.tanggal
                }
            }

            override fun onFailure(call: Call<Pengajian>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}",
                    Toast.LENGTH_SHORT).show()
            }

        })
    }
}
