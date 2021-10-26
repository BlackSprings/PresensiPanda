package com.presensi.panda.activities.main.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.presensi.panda.R
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.activities.main.ResponseAttendance
import com.presensi.panda.databinding.FragmentScannerBinding
import com.presensi.panda.models.AttendanceEmployee
import com.presensi.panda.network.ApiConfig
import com.presensi.panda.network.AttendanceRequest
import com.presensi.panda.ui.DialogFragment
import com.presensi.panda.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ScannerFragment : Fragment() {

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(activity is MainActivity){
            (activity as MainActivity).setBottomNavigationVisibility(View.GONE)
        }
        setupPermissions()
        codeScanner()
    }

    private fun codeScanner() {
        val scannerView = binding.scannerView
        codeScanner = CodeScanner(requireActivity(), scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {
                    postAttendance(it.text)
                    codeScanner.stopPreview()
                }
            }

            errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    Log.e("Main", "codeScanner: ${it.message}")
                }
            }

            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQ
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQ -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        requireActivity(),
                        "Mohon izinkan aplikasi ini untuk mengakses kamera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        var busyDialogFragment = DialogFragment()
        if (isLoading) {
            busyDialogFragment.show(parentFragmentManager)
        } else {
            busyDialogFragment =
                parentFragmentManager.findFragmentByTag(DialogFragment.FRAGMENT_TAG) as DialogFragment
            busyDialogFragment.dismiss()
        }
    }

    private fun postAttendance(attendanceId: String) {
        showLoading(true)
        val sharedPrefManager = SharedPrefManager.getInstance(requireContext())
        val employeeId = sharedPrefManager.employee.id
        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())
        val attendance = AttendanceRequest(employeeId!!, attendanceId, currentDate)
        val client = ApiConfig.getApiService(requireContext()).postAttendance("Bearer ${sharedPrefManager.auth.token}",attendance)
        client.enqueue(object : Callback<ResponseAttendance>{
            override fun onResponse(
                call: Call<ResponseAttendance>,
                response: Response<ResponseAttendance>
            ) {
                showLoading(false)
                binding.scannerView.visibility = View.GONE
                val responseBody = response.body()
                if(responseBody?.totalData!! == 1){
                    var sharedPrefManager = SharedPrefManager.getInstance(requireContext())
                    //clear local attendance employee
                    sharedPrefManager.clear(SharedPrefManager.SHARED_PREF_ATTENDANCE)
                    //save attendance employee
                    var attendanceEmployee = AttendanceEmployee(
                        responseBody?.data?.id!!,
                        responseBody?.data?.localDate,
                        responseBody?.data?.checkIn,
                        responseBody?.data?.checkOut,
                        responseBody?.data?.status,
                        responseBody?.data?.attendanceId,
                        responseBody?.data?.employeeId!!
                    )
                    Log.d(tag, "onResponseBody: ${responseBody.toString()} token: ${sharedPrefManager.auth.token.toString()}")
                    sharedPrefManager.saveAttendanceEmployee(attendanceEmployee)
                    sharedPrefManager.saveMessage(responseBody?.message.toString())
                    Toast.makeText(requireContext(), "Data Saved", Toast.LENGTH_SHORT).show()
                    val mResultFragment = ResultFragment()
                    val mFragmentManager = parentFragmentManager
                    mFragmentManager.beginTransaction().apply {
                        replace(R.id.frameLayout, mResultFragment, ResultFragment::class.java.simpleName)
                        addToBackStack(null)
                        commit()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseAttendance>, t: Throwable) {
                showLoading(false)
                binding.scannerView.visibility = View.GONE
                Log.e(tag, "onFailureScanner: ${t.message} token: ${sharedPrefManager.auth.token}")
                val mResultFragment = ResultFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameLayout, mResultFragment, ResultFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        })
    }

    companion object {
        private const val CAMERA_REQ = 101
    }

}