package com.presensi.panda.activities.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.presensi.panda.activities.profile.fragments.ProfileFragment
import com.presensi.panda.R
import com.presensi.panda.activities.login.LoginActivity
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.databinding.ActivityProfileBinding
import com.presensi.panda.utils.SharedPrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val mFragmentManager = supportFragmentManager
        val mProfileFragment = ProfileFragment()
        val fragment = mFragmentManager.findFragmentByTag(ProfileFragment::class.java.simpleName)

        if (fragment !is ProfileFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frameLayout, mProfileFragment, ProfileFragment::class.java.simpleName)
                .commit()
        }

        binding.bottomNavigationView.selectedItemId = R.id.profile_nav
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
                item: MenuItem -> selectedMenu(item)
            false
        }
    }

    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun selectedMenu(item: MenuItem){
        item.isChecked = true
        when(item.itemId){
            R.id.home_nav -> {
                val moveProfile = Intent(this@ProfileActivity, MainActivity::class.java)
                moveProfile.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(moveProfile)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val moveProfile = Intent(this@ProfileActivity, MainActivity::class.java)
        moveProfile.flags =  Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(moveProfile)
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        binding.bottomNavigationView.visibility = visibility
    }
}