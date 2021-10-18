package com.presensi.panda

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.presensi.panda.databinding.FragmentProfileBinding
import com.presensi.panda.databinding.FragmentScannerBinding

class ProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imgAbout:ImageView = view.findViewById(R.id.imgToAbout)
        val imgHelp:ImageView = view.findViewById(R.id.imgToHelp)
        imgAbout.setOnClickListener(this)
        imgHelp.setOnClickListener(this)
        var actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.hide()
        if(activity is ProfileActivity){
            (activity as ProfileActivity).setBottomNavigationVisibility(View.VISIBLE)
        }

        binding.btnLogout.setOnClickListener {
            val moveLogin = Intent(activity, LoginActivity::class.java)
            startActivity(moveLogin)
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