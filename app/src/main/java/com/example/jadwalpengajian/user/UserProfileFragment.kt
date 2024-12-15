package com.example.jadwalpengajian.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jadwalpengajian.LoginActivity
import com.example.jadwalpengajian.R
import com.example.jadwalpengajian.databinding.FragmentUserProfileBinding

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserProfileBinding.bind(view)

        // Ambil data user dari SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", 0)
        val userName = sharedPreferences.getString("username", "User")

        // Menampilkan nama user
        binding.tvName.text = userName

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
