package com.presensi.panda

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.presensi.panda.databinding.FragmentChangePasswordBinding
import com.presensi.panda.databinding.FragmentHomeBinding

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
                Toast.makeText(activity, "Not Implemented Yet",Toast.LENGTH_SHORT).show()
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

        if(!confirmPassword.isEmpty() && confirmPassword !== newPassword){
            isValid = false
            binding.outlinePassword.error = "Password tidak sama"
            binding.outlineConfirmPassword.error = "Password tidak sama"
        }

        return isValid
    }

}