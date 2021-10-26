package com.presensi.panda.activities.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.presensi.panda.models.Attendance
import com.presensi.panda.R
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.activities.main.adapters.ListAttendanceAdapter
import com.presensi.panda.databinding.FragmentHomeBinding
import com.presensi.panda.utils.SharedPrefManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
        val sharedPrefManager = SharedPrefManager.getInstance(requireContext())
        binding.tvCompanyName.text = sharedPrefManager.employee.company_name
        binding.tvEmployeeName.text = sharedPrefManager.employee.name
        binding.tvPosition.text = sharedPrefManager.employee.position

        var check_in = sharedPrefManager.attendanceEmployee.check_in
        var check_out = sharedPrefManager.attendanceEmployee.check_out
        var local_date = sharedPrefManager.attendanceEmployee.local_date

        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())

        Log.d(tag,"onGetSharedAttendance: ${sharedPrefManager.isAttendance}")

        binding.btnCheckin.setOnClickListener {
            if(check_in != null && currentDate == local_date){
                DynamicToast.makeWarning(requireContext(),"Anda Sudah Check In Hari Ini",Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.TOP, 0, 0)
                    show()
                }
            }else{
                val mScannerFragment = ScannerFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout, mScannerFragment, ScannerFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }

        binding.btnCheckout.setOnClickListener {
            if(check_out != null && currentDate == local_date){
                DynamicToast.makeWarning(requireContext(),"Anda Sudah Check Out Hari Ini",Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.TOP, 0, 0)
                    show()
                }
            }else if(check_in == null){
                DynamicToast.makeWarning(requireContext(),"Anda Belum Check In Hari Ini",Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.TOP, 0, 0)
                    show()
                }
            }else{
                val mScannerFragment = ScannerFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout, mScannerFragment, ScannerFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
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