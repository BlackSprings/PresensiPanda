package com.presensi.panda.activities.profile.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.presensi.panda.R
import com.presensi.panda.activities.profile.ProfileActivity
import com.presensi.panda.databinding.FragmentHelpBinding
import com.presensi.panda.network.Reference

class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actionBar = (activity as AppCompatActivity).supportActionBar;
        actionBar?.show()
        actionBar?.title = "Bantuan"
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#279BD5")))
        actionBar?.setDisplayHomeAsUpEnabled(false);
        if(activity is ProfileActivity){
            (activity as ProfileActivity).setBottomNavigationVisibility(View.GONE)
        }

        binding.imgPandaWeb.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.pandaWeb)
            startActivity(openUrl)
        }

        binding.tvPandaWeb.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.pandaWeb)
            startActivity(openUrl)
        }

        binding.imgPuskoWeb.setOnClickListener{
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.puskoWeb)
            startActivity(openUrl)
        }

        binding.tvPuskomediaWeb.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.puskoWeb)
            startActivity(openUrl)
        }

        binding.imgFPusko.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.facePanda)
            startActivity(openUrl)
        }

        binding.tvFPusko.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.facePanda)
            startActivity(openUrl)
        }

        binding.tvIgPanda.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.igPanda)
            startActivity(openUrl)
        }

        binding.imgIgPanda.setOnClickListener {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(Reference.igPanda)
            startActivity(openUrl)
        }
    }

}