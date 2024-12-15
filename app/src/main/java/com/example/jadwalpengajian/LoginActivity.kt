package com.example.jadwalpengajian

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jadwalpengajian.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private var username: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Periksa apakah pengguna sudah login
        checkLoginStatus()

        setContentView(binding.root)

        binding.apply {
            etUsername.setText(username)
            etPassword.setText(password)

            btnLogin.setOnClickListener {
                onLoginClick()
            }
        }
    }

    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val loggedInUser = sharedPreferences.getString("username", null)
        val role = sharedPreferences.getString("role", null)

        if (!loggedInUser.isNullOrEmpty() && !role.isNullOrEmpty()) {
            // Jika sudah login, langsung arahkan ke halaman sesuai role
            if (role == "admin") {
                navigateToAdminHome()
            } else if (role == "user") {
                navigateToUserHome()
            }
        }
    }

    fun onLoginClick() {
        username = binding.etUsername.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            checkLogin(username, password)
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkLogin(username: String, password: String) {
        // Dummy validation
        if (username == "admin" && password == "admin123") {
            saveLoginData(username, "admin")
            navigateToAdminHome()
        } else if (username == "user" && password == "user123") {
            saveLoginData(username, "user")
            navigateToUserHome()
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveLoginData(username: String, role: String) {
        val sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("role", role)
        editor.apply()
    }

    private fun navigateToAdminHome() {
        val intent = Intent(this, MainActivityAdmin::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToUserHome() {
        val intent = Intent(this, MainActivityUser::class.java)
        startActivity(intent)
        finish()
    }
}
