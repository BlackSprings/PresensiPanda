package com.presensi.panda.activities.profile.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.presensi.panda.activities.profile.ProfileActivity
import com.presensi.panda.activities.profile.ResponseChangePassword
import com.presensi.panda.databinding.FragmentChangePasswordBinding
import com.presensi.panda.network.ApiConfig
import com.presensi.panda.network.ChangePasswordRequest
import com.presensi.panda.ui.DialogFragment
import com.presensi.panda.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var actionBar = (activity as AppCompatActivity).supportActionBar;
        actionBar?.hide()
        if(activity is ProfileActivity){
            (activity as ProfileActivity).setBottomNavigationVisibility(View.GONE)
        }

        binding.txtPassword.doOnTextChanged { text, start, before, count -> binding.outlinePassword.isErrorEnabled = false }
        binding.txtOldPassword.doOnTextChanged { text, start, before, count -> binding.outlineOldPassword.isErrorEnabled = false }
        binding.txtConfirmPassword.doOnTextChanged { text, start, before, count -> binding.outlineConfirmPassword.isErrorEnabled = false }

        binding.btnSave.setOnClickListener {
            if(validateData()){
                changePassword()
            }
        }

    }

    private fun validateData() : Boolean{
        var isValid = true
        val oldPassword = binding.txtOldPassword.text.toString()
        val newPassword = binding.txtPassword.text.toString()
        val confirmPassword = binding.txtConfirmPassword.text.toString()

        if(oldPassword.isEmpty()){
            isValid = false
            binding.outlineOldPassword.error = "Password lama tidak boleh kosong."
        }

        if(newPassword.isEmpty()){
            isValid = false
            binding.outlinePassword.error = "Password Baru tidak boleh kosong."
        }

        if(confirmPassword.isEmpty()){
            isValid = false
            binding.outlineConfirmPassword.error = "Konfirmasi Password tidak boleh kosong."
        }

        if(newPassword.length < 6){
            isValid = false
            binding.outlinePassword.error = "Password Baru minimal 6 karakter."
        }

        if(confirmPassword.length < 6){
            isValid = false
            binding.outlineConfirmPassword.error = "Konfirmasi Password minimal 6 karakter."
        }

        if(confirmPassword.isNotEmpty() && confirmPassword != newPassword){
            isValid = false
            binding.outlinePassword.error = "Password tidak sama"
            binding.outlineConfirmPassword.error = "Password tidak sama"
        }

        return isValid
    }

    private fun changePassword(){
        showLoading(true)
        val oldPassword = binding.txtOldPassword.text
        val password = binding.txtPassword.text
        val confirmationPassword = binding.txtConfirmPassword.text

        val sharedPrefManager = SharedPrefManager.getInstance(requireContext())

        val request = ChangePasswordRequest(oldPassword.toString(), password.toString(), confirmationPassword.toString())
        val client = ApiConfig.getApiService(requireContext()).postChangePassword("Bearer ${sharedPrefManager.auth.token.toString()}", sharedPrefManager.user.id, request)
        client.enqueue(object: Callback<ResponseChangePassword>{
            override fun onResponse(
                call: Call<ResponseChangePassword>,
                response: Response<ResponseChangePassword>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if(responseBody?.status == 200){
                    binding.txtConfirmPassword.text = null
                    binding.txtOldPassword.text = null
                    binding.txtPassword.text = null
                    DynamicToast.makeSuccess(requireContext(), "Password Berhasil Dirubah.", Toast.LENGTH_LONG).apply {
                        setGravity(Gravity.TOP, 0, 0)
                        show()
                    }
                    Log.d(tag, "onResponseChangePassword: ${response.body().toString()}")
                }else{
                    Log.d(tag,"onFailureChangePassword: ${response.body().toString()}")
                    DynamicToast.makeError(requireContext(), "Password Lama tidak sesuai.", Toast.LENGTH_LONG).apply {
                        setGravity(Gravity.TOP, 0, 0)
                        show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseChangePassword>, t: Throwable) {
                showLoading(false)
                DynamicToast.makeError(requireContext(), "Password Gagal Dirubah.", Toast.LENGTH_LONG).apply {
                    setGravity(Gravity.TOP, 0,0)
                    show()
                }
                Log.e(tag, "onFailureChangePassword: ${t.message}")
            }

        })
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

}