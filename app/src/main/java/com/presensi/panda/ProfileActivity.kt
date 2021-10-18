package com.presensi.panda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.presensi.panda.databinding.ActivityProfileBinding

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

    private fun selectedMenu(item: MenuItem){
        item.isChecked = true
        when(item.itemId){
            R.id.home_nav -> {
                val moveProfile = Intent(this@ProfileActivity, MainActivity::class.java)
                startActivity(moveProfile)
            }
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        // get the reference of the bottomNavigationView and set the visibility.
        binding.bottomNavigationView.visibility = visibility
    }
}