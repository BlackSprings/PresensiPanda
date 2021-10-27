package com.presensi.panda.activities.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.presensi.panda.R
import com.presensi.panda.activities.brand.BrandingActivity
import com.presensi.panda.activities.login.LoginActivity
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.utils.SharedPrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val SplashTimeOut : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            delay(SplashTimeOut)
            finish()
            if(SharedPrefManager.getInstance(this@SplashActivity).isLoggedIn){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
            startActivity(Intent(this@SplashActivity, BrandingActivity::class.java))
        }
    }
}