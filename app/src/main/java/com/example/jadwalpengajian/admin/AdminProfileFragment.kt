package com.example.jadwalpengajian.admin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jadwalpengajian.LoginActivity
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.databinding.FragmentAdminProfileBinding

class AdminProfileFragment : Fragment(R.layout.fragment_admin_profile) {

    private lateinit var binding: FragmentAdminProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdminProfileBinding.bind(view)

        // Contoh data admin (dapat diambil dari SharedPreferences atau API)
        val sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", 0)
        val adminName = sharedPreferences.getString("username", "Admin User")

        // Menampilkan nama admin
        binding.tvName.text = adminName

        // Logika tombol logout
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Hapus data login dari SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", 0)
        sharedPreferences.edit().clear().apply()

        // Arahkan ke halaman login
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
