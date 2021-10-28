package com.presensi.panda.activities.profile.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.presensi.panda.R
import com.presensi.panda.activities.brand.BrandingActivity
import com.presensi.panda.activities.login.LoginActivity
import com.presensi.panda.activities.profile.ProfileActivity
import com.presensi.panda.databinding.FragmentProfileBinding
import com.presensi.panda.utils.SharedPrefManager

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
        val imgChangePassword:ImageView = view.findViewById(R.id.imgToChangePwd)
        imgAbout.setOnClickListener(this)
        imgHelp.setOnClickListener(this)
        imgChangePassword.setOnClickListener(this)
        var actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.hide()
        if(activity is ProfileActivity){
            (activity as ProfileActivity).setBottomNavigationVisibility(View.VISIBLE)
        }

        binding.btnLogout.setOnClickListener {
            var sharedPrefManager = SharedPrefManager.getInstance(requireContext())
            sharedPrefManager.clear(SharedPrefManager.SHARED_PREF_NAME)
            sharedPrefManager.clear(SharedPrefManager.SHARED_PREF_AUTH)
            sharedPrefManager.clear(SharedPrefManager.SHARED_PREF_EMPLOYEE)
            sharedPrefManager.clear(SharedPrefManager.SHARED_PREF_ATTENDANCE)
            sharedPrefManager.clear(SharedPrefManager.SHARED_PREF_SERVER)
            val moveLogin = Intent(activity, BrandingActivity::class.java)
            moveLogin.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(moveLogin)
            (activity as AppCompatActivity).finish()
        }
        val sharedPrefManager = SharedPrefManager.getInstance(requireContext())
        binding.txtName.text =  sharedPrefManager.user.name
        binding.txtEmail.text = sharedPrefManager.user.username

        binding.txtNameDetail.text = sharedPrefManager.employee.name
        binding.txtEmailDetail.text = sharedPrefManager.employee.position

    }

    override fun onClick(v: View) {
        if (v.id == R.id.imgToAbout || v.id == R.id.txt_about){
            val mAboutFragment = AboutAppFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mAboutFragment, mAboutFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        if(v.id == R.id.imgToHelp || v.id == R.id.txt_contact){
            val mHelpFragment = HelpFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mHelpFragment, HelpFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        if(v.id == R.id.imgToChangePwd || v.id == R.id.txt_change_pwd){
            val mChangePasswordFragment = ChangePasswordFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mChangePasswordFragment, ChangePasswordFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

}