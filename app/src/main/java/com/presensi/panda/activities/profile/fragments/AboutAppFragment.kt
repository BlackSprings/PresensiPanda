package com.presensi.panda.activities.profile.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.presensi.panda.R
import com.presensi.panda.activities.profile.ProfileActivity

class AboutAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        actionBar?.title = "Tentang Aplikasi"
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#279BD5")))
        actionBar?.setDisplayHomeAsUpEnabled(false);
        if(activity is ProfileActivity){
            (activity as ProfileActivity).setBottomNavigationVisibility(View.GONE)
        }
    }

}