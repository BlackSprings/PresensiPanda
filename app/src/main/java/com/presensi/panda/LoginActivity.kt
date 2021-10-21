package com.presensi.panda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.presensi.panda.databinding.ActivityLoginBinding
import com.presensi.panda.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        
        binding.txtUsername.doOnTextChanged { text, start, before, count -> binding.outlineUsername.isErrorEnabled = false }
        binding.txtPassword.doOnTextChanged { text, start, before, count -> binding.outlinePassword.isErrorEnabled = false }

        binding.btnLogin.setOnClickListener{
            if(validateData()){
                val moveMainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(moveMainActivity)
            }
        }
    }

    private fun validateData(): Boolean {
        var isValid = true;
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        if(username.isEmpty()){
            isValid = false
            binding.outlineUsername.error = "Username tidak boleh kosong."
        }

        if(password.isEmpty()){
            isValid = false
            binding.outlinePassword.error = "Password tidak boleh kosong."
        }

        return isValid
    }


}