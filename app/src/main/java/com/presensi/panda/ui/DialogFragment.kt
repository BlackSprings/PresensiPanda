package com.presensi.panda.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.presensi.panda.ui.DialogFragment as BusyDialogFragment
import androidx.fragment.app.FragmentManager
import com.presensi.panda.R

class DialogFragment : DialogFragment() {

    companion object {
        const val FRAGMENT_TAG = "busy"
    }

    private fun newInstance() = BusyDialogFragment()

    fun show(supportFragment: FragmentManager): BusyDialogFragment {
        val dialog = newInstance()
        dialog.isCancelable = false
        dialog.show(supportFragment, FRAGMENT_TAG)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return requireActivity().layoutInflater.inflate(R.layout.fragment_dialog, container)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.also { window ->
            window.attributes?.also { attributes ->
                attributes.dimAmount = 0.1f
                window.attributes = attributes
            }
        }
    }


}