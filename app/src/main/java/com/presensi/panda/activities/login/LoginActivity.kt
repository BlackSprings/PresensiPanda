package com.presensi.panda.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.databinding.ActivityLoginBinding
import com.presensi.panda.models.User
import com.presensi.panda.network.ApiConfig
import com.presensi.panda.ui.DialogFragment
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.txtUsername.doOnTextChanged { _, _, _, _ ->
            binding.outlineUsername.isErrorEnabled = false
        }
        binding.txtPassword.doOnTextChanged { _, _, _, _ ->
            binding.outlinePassword.isErrorEnabled = false
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()
            if (validateData()) {
                  Log.d(TAG, "onValidate ${username} ${password}")
                  postLogin(username, password)
            }
        }
    }

    private fun validateData(): Boolean {
        var isValid = true;
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        if (username.isEmpty()) {
            isValid = false
            binding.outlineUsername.error = "Username tidak boleh kosong."
        }

        if (password.isEmpty()) {
            isValid = false
            binding.outlinePassword.error = "Password tidak boleh kosong."
        }
        return isValid
    }

    private fun postLogin(username: String, password: String) {
        showLoading(true)
        var user = User(username,password)
        val client = ApiConfig.getApiService().postLogin(user)
        client.enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                showLoading(false)
                val responseBody = response.body()
                if(responseBody != null){
                    if(responseBody.totalData!! > 0){
                        Toast.makeText(this@LoginActivity, "hai ${responseBody.data?.name}",Toast.LENGTH_LONG).show()
                    }else{
                        val dlgAlert: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)

                        dlgAlert.setMessage("wrong password or username")
                        dlgAlert.setTitle("Error Message...")
                        dlgAlert.setPositiveButton("OK", null)
                        dlgAlert.setCancelable(true)
                        dlgAlert.create().show()

                        dlgAlert.setPositiveButton("Ok",
                            DialogInterface.OnClickListener { dialog, which -> })
                    }
                }else{
                    Log.e(TAG, "onFailureLoginPost: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailureLogin: ${t.message}")
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        var busyDialogFragment = DialogFragment()
        if (isLoading) {
            busyDialogFragment.show(supportFragmentManager)
        } else {
            busyDialogFragment = supportFragmentManager.findFragmentByTag(DialogFragment.FRAGMENT_TAG) as DialogFragment
            busyDialogFragment.dismiss()
        }
    }


}