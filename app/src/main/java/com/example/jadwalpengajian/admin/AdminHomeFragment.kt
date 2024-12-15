package com.example.jadwalpengajian.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.ScheduleAdapterAdmin
import com.example.jadwalpengajian.data.Pengajian
import com.example.jadwalpengajian.data.network.ApiClient
import com.example.jadwalpengajian.data.network.ApiService
import com.example.jadwalpengajian.databinding.FragmentAdminHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHomeFragment : Fragment(R.layout.fragment_admin_home) {

    private lateinit var binding: FragmentAdminHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminHomeBinding.bind(view)

        // Tombol tambah jadwal
        binding.addSchedule.setOnClickListener {
            findNavController().navigate(R.id.action_adminHomeFragment_to_adminCreateFragment)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val retrofit = ApiClient.getInsance().create(ApiService::class.java)
        retrofit.getAllPengajian().enqueue(object : Callback<List<Pengajian>> {
            override fun onResponse(
                call: Call<List<Pengajian>>,
                response: Response<List<Pengajian>>
            ) {
                Log.d("AdminHomeFragment", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val pengajian = response.body()
                    val adapter = ScheduleAdapterAdmin(
                        scheduleList = pengajian ?: emptyList(),
                        onEditClick = { pengajianItem ->
                            val bundle = Bundle().apply {
                                pengajianItem._id?.let { putString("pengajianId", it) }
                            }
                            // Navigasi ke halaman edit
                            findNavController().navigate(
                                R.id.action_adminHomeFragment_to_adminEditFragment,
                                bundle
                            )
                        },
                        onDeleteClick = { pengajianItem ->
                            pengajianItem._id?.let { id ->
                                deletePengajian(id) { success ->
                                    if (success) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Data berhasil dihapus",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        // Refresh data setelah penghapusan
                                        setupRecyclerView()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Gagal menghapus data",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        },
                        onDetailClick = { pengajianItem -> // Navigasi ke halaman detail
                            val bundle = Bundle().apply {
                                pengajianItem._id?.let { putString("pengajianId", it) }
                            }
                            findNavController().navigate(
                                R.id.action_adminHomeFragment_to_adminDetailFragment,
                                bundle
                            )
                        }
                    )
                    binding.rvScheduleAdmin.apply {
                        this.adapter = adapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memuat data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Pengajian>>, t: Throwable) {
                Toast.makeText(requireContext(), "Gagal memanggil API: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deletePengajian(id: String, callback: (Boolean) -> Unit) {
        val retrofit = ApiClient.getInsance().create(ApiService::class.java)
        retrofit.deletePengajian(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("AdminHomeFragment", "Error deleting data: ${t.message}")
                callback(false)
            }
        })
    }
}
