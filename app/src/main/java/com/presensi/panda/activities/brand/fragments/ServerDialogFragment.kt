package com.presensi.panda.activities.brand.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.presensi.panda.R
import com.presensi.panda.activities.brand.BrandingActivity
import com.presensi.panda.databinding.FragmentServerDialogBinding

class ServerDialogFragment : DialogFragment() {

    private var optionDialogListener: OnOptionDialogListener? = null
    private var _binding: FragmentServerDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentServerDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnChoose.setOnClickListener{
            val checkedRadioButtonId = binding.rgOptions.checkedRadioButtonId
            if(checkedRadioButtonId != -1){
                var coach: String? = null
                when(checkedRadioButtonId){
                    R.id.rb_sipanda -> coach = binding.rbSipanda.text.toString().trim()
                    R.id.rb_sidemit -> coach = binding.rbSidemit.text.toString().trim()
                    R.id.rb_simba -> coach = binding.rbSimba.text.toString().trim()
                    R.id.rb_sinar -> coach = binding.rbSinar.text.toString().trim()
                }
                optionDialogListener?.onOptionChosen(coach)
                dialog?.dismiss()
            }
        }

        binding.btnClose.setOnClickListener{
            dialog?.cancel()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val brandingActivity = activity
        if(brandingActivity is BrandingActivity){
            this.optionDialogListener = brandingActivity.serverDialogFragment
        }
    }

    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }
}