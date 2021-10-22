package com.presensi.panda.activities.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.presensi.panda.models.Attendance
import com.presensi.panda.R
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.activities.main.adapters.ListAttendanceAdapter
import com.presensi.panda.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val listAttendance = ArrayList<Attendance>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAttendances.setHasFixedSize(true)
        listAttendance.addAll(listAttendances)
        showRecyclerList()
        binding.btnCheckin.setOnClickListener {
            val mScannerFragment = ScannerFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mScannerFragment, ScannerFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnCheckout.setOnClickListener {
            val mScannerFragment = ScannerFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, mScannerFragment, ScannerFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }

        if(activity is MainActivity){
            (activity as MainActivity).setBottomNavigationVisibility(View.VISIBLE)
        }
    }

    private val listAttendances: ArrayList<Attendance>
        get() {
            val listData = ArrayList<Attendance>()
            for(i in 1..10){
                var attendance = Attendance("Sabtu","13 Oktober 2021","08:00","12:00")
                listData.add(attendance)
            }
            return listData
        }

    private fun showRecyclerList() {
        binding.rvAttendances.layoutManager = LinearLayoutManager(activity)
        val listAttendanceAdapter = ListAttendanceAdapter(listAttendance)
        binding.rvAttendances.adapter = listAttendanceAdapter
    }

}