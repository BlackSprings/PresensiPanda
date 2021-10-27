package com.presensi.panda.activities.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.presensi.panda.models.Attendance
import com.presensi.panda.R
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.activities.main.ResponseLogAttendanceEmployee
import com.presensi.panda.activities.main.adapters.ListAttendanceAdapter
import com.presensi.panda.databinding.FragmentHomeBinding
import com.presensi.panda.network.ApiConfig
import com.presensi.panda.network.DummyRequest
import com.presensi.panda.utils.SharedPrefManager
import com.presensi.panda.view_models.AttendanceViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

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

        val attendanceViewModel = ViewModelProvider(requireActivity(),ViewModelProvider.NewInstanceFactory()).get(AttendanceViewModel::class.java)
        attendanceViewModel.listReview.observe(viewLifecycleOwner, { listAttendance -> setHistoriesAttendance(listAttendance) })

        binding.rvAttendances.layoutManager = LinearLayoutManager(activity)

        val sharedPrefManager = SharedPrefManager.getInstance(requireContext())
        binding.tvCompanyName.text = sharedPrefManager.employee.company_name
        binding.tvEmployeeName.text = sharedPrefManager.employee.name
        binding.tvPosition.text = sharedPrefManager.employee.position

        attendanceViewModel.getHistoriesByEmployeeId(requireContext(),
            sharedPrefManager.auth.token!!, sharedPrefManager.employee.id!!)

        val check_in = sharedPrefManager.attendanceEmployee.check_in
        val check_out = sharedPrefManager.attendanceEmployee.check_out
        val local_date = sharedPrefManager.attendanceEmployee.local_date

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

    private fun setHistoriesAttendance(listAttendance: List<Attendance>) {
        if(listAttendance.isEmpty()){
            binding.rvAttendances.visibility = View.INVISIBLE
            binding.emptyRow.visibility = View.VISIBLE
            binding.emptyRow.text = getString(R.string.empty_attendance_log)
        }else{
            binding.rvAttendances.visibility = View.VISIBLE
            binding.emptyRow.visibility = View.GONE
        }
        val adapter = ListAttendanceAdapter(listAttendance)
        binding.rvAttendances.adapter = adapter
    }

}