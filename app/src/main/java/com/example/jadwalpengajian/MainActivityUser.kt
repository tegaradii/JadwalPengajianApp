package com.example.jadwalpengajian

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jadwalpengajian.databinding.ActivityMainUserBinding

class MainActivityUser : AppCompatActivity() {
    private lateinit var binding: ActivityMainUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding
        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val navController = findNavController(R.id.nav_host_user_fragment)
            bottomNavigationView.setupWithNavController(navController)
        }

    }
}