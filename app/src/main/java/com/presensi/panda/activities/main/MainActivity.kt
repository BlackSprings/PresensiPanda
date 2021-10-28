package com.presensi.panda.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.presensi.panda.activities.main.fragments.HomeFragment
import com.presensi.panda.activities.profile.ProfileActivity
import com.presensi.panda.R
import com.presensi.panda.activities.brand.BrandingActivity
import com.presensi.panda.activities.login.LoginActivity
import com.presensi.panda.databinding.ActivityMainBinding
import com.presensi.panda.utils.SharedPrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val mFragmentManager = supportFragmentManager
        val mHomeFragment = HomeFragment()
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frameLayout, mHomeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            item: MenuItem -> selectedMenu(item)
            false
        }
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNavigationView.selectedItemId = R.id.home_nav
        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, BrandingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun selectedMenu(item: MenuItem){
        item.isChecked = true
        when(item.itemId){
            R.id.profile_nav -> {
                val moveProfile = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(moveProfile)
            }
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        binding.bottomNavigationView.visibility = visibility
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan kembali lagi untuk keluar", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            delay(2000)
            doubleBackToExitPressedOnce = false
        }
    }
}