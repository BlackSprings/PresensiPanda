package com.presensi.panda.activities.brand

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.presensi.panda.R
import com.presensi.panda.activities.brand.fragments.ServerDialogFragment
import com.presensi.panda.activities.login.LoginActivity
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.databinding.ActivityBrandingBinding
import com.presensi.panda.network.Reference
import com.presensi.panda.utils.SharedPrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BrandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBrandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnServer.setOnClickListener {
            val mOptionDialogFragment = ServerDialogFragment()

            val mFragmentManager = supportFragmentManager
            mOptionDialogFragment.show(mFragmentManager, ServerDialogFragment::class.java.simpleName)
        }
    }

    internal var serverDialogFragment: ServerDialogFragment
    .OnOptionDialogListener = object : ServerDialogFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            val sharedPrefManager = SharedPrefManager.getInstance(applicationContext)
            var server = ""
            if(text.equals("sipanda",true)){
                server = Reference.sipanda
            }

            if(text.equals("sidemit",true)){
                server = Reference.sidemit
            }

            if(text.equals("simba",true)){
                server = Reference.simba
            }

            if(text.equals("sinar",true)){
                server = Reference.sinar
            }
            sharedPrefManager.saveServer(server)
            Toast.makeText(this@BrandingActivity, "Server Tersimpan.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@BrandingActivity, LoginActivity::class.java))
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan kembali lagi untuk keluar", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            delay(2000)
            doubleBackToExitPressedOnce = false
        }
    }

    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

//        if(SharedPrefManager.getInstance(this).server.isNotEmpty()){
//            val intent = Intent(applicationContext, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
    }


}