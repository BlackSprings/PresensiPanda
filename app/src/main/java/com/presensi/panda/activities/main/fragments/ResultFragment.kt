package com.presensi.panda.activities.main.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.presensi.panda.R
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.databinding.FragmentResultBinding
import com.presensi.panda.utils.SharedPrefManager


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener{
            val mHomeFragment = HomeFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mHomeFragment, HomeFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(requireContext()).isAttendance){
            binding.iconResult.setImageResource(R.drawable.ic_illustration_selesai)
            val sharedPrefManager = SharedPrefManager.getInstance(requireContext())
            val resultMessage = sharedPrefManager.message.split(" ")
            Log.d(tag, "onResultMessage ${resultMessage}")
            if(resultMessage.size == 5){
                binding.resultMessage1.text = "${resultMessage[0]} ${resultMessage[1]}"
                binding.resultMessage2.text = resultMessage[2]
                binding.resultMessage3.text = "${resultMessage[3]} ${resultMessage[4]}"
            }
            else if(resultMessage.size > 5){
                binding.resultMessage1.text = "${resultMessage[0]} ${resultMessage[1]}"
                binding.resultMessage2.text = "${resultMessage[2]} ${resultMessage[3]}"
                binding.resultMessage3.text = resultMessage[4]
            }else{
                binding.resultMessage1.text = sharedPrefManager.message
            }
        }else{
            binding.iconResult.setImageResource(R.drawable.ic_baseline_close_24)
            binding.resultMessage1.text = "Mohon Maaf terjadi kesalahan."
            binding.resultMessage2.visibility = View.INVISIBLE
            binding.resultMessage3.text = "Harap Mencoba Kembali."
        }
    }

}