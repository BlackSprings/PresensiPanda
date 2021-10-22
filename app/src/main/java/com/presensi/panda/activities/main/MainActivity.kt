package com.presensi.panda.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.presensi.panda.models.Attendance
import com.presensi.panda.activities.main.fragments.HomeFragment
import com.presensi.panda.activities.profile.ProfileActivity
import com.presensi.panda.R
import com.presensi.panda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val listAttendance = ArrayList<Attendance>()
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

        binding.bottomNavigationView.selectedItemId = R.id.home_nav
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            item: MenuItem -> selectedMenu(item)
            false
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
}