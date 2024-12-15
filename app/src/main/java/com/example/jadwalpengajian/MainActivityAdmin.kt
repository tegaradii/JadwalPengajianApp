package com.example.jadwalpengajian

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jadwalpengajian.databinding.ActivityMainAdminBinding

class MainActivityAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityMainAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val navController = findNavController(R.id.nav_host_admin_fragment)
            bottomNavigationView.setupWithNavController(navController)
        }

    }
}