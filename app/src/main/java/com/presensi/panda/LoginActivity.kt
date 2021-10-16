package com.presensi.panda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.presensi.panda.databinding.ActivityLoginBinding
import com.presensi.panda.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener{
            val moveMainActivity = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveMainActivity)
        }
    }
}