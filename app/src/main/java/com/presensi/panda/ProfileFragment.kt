package com.presensi.panda

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProfileFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imgAbout:ImageView = view.findViewById(R.id.imgToAbout)
        val imgHelp:ImageView = view.findViewById(R.id.imgToHelp)
        imgAbout.setOnClickListener(this)
        imgHelp.setOnClickListener(this)
        var actionBar = (activity as AppCompatActivity).supportActionBar;
        actionBar?.hide()
        if(activity is ProfileActivity){
            (activity as ProfileActivity).setBottomNavigationVisibility(View.VISIBLE)
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgToAbout){
            val mAboutFragment = AboutAppFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mAboutFragment, mAboutFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        if(v.id == R.id.imgToHelp){
            val mHelpFragment = HelpFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mHelpFragment, HelpFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

}