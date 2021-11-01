package com.presensi.panda.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.presensi.panda.databinding.ActivityLoginBinding
import com.presensi.panda.network.UserRequest
import com.presensi.panda.network.ApiConfig
import com.presensi.panda.ui.DialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.Gravity
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.presensi.panda.activities.brand.BrandingActivity
import com.presensi.panda.activities.main.MainActivity
import com.presensi.panda.models.Auth
import com.presensi.panda.models.Employee as EmployeeData
import com.presensi.panda.models.User
import com.presensi.panda.utils.SharedPrefManager


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
        val user = UserRequest(username, password)
        val client = ApiConfig.getApiService(applicationContext).postLogin(user)
        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                showLoading(false)
                val responseBody = response.body()
                if (responseBody != null) {
                    if (responseBody.totalData!! > 0) {
                        if(responseBody.data?.roles?.get(0)?.name == "operator:User"){

                            //save user
                            var user = User(
                                responseBody.data.id!!,
                                responseBody.data.name,
                                responseBody.data.username,
                                responseBody.data.email
                            )
                            SharedPrefManager.getInstance(applicationContext).saveUser(user)

                            //save auth
                            var auth = Auth(
                                responseBody.token,
                                responseBody.expiresIn,
                                responseBody.tokenType
                            )
                            SharedPrefManager.getInstance(applicationContext).saveAuth(auth)

                            //save employee
                            var employee = EmployeeData(
                                responseBody.data.employee?.id,
                                responseBody.data.employee?.userId,
                                responseBody.data.employee?.idnumber,
                                responseBody.data.employee?.name,
                                responseBody.data.employee?.regionMendagriCode,
                                responseBody.data.employee?.regionName,
                                responseBody.data.employee?.position,
                                responseBody.data.employee?.pob,
                                responseBody.data.employee?.dob.toString(),
                                responseBody.data.employee?.gender,
                                responseBody.data.employee?.address.toString()
                            )
                            SharedPrefManager.getInstance(applicationContext).saveEmployee(employee)

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)
                        }else{
                            showDialog("Role akun anda bukan Operator User.")
                        }
                        Log.d(TAG, "DataInfo: ${responseBody.data}")
                    } else {
                        showDialog(responseBody.message.toString())
                    }
                } else {
                    Log.e(TAG, "onFailureLoginPost: ${response.message()}")
                    showDialog("Username dan Password tidak dapat Kami Kenali")
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                showLoading(false)
                if(t.message.toString().contains("IllegalStateException",ignoreCase = false)){
                    showDialog("Username dan Password tidak dapat Kami Kenali")
                }
                Log.e(TAG, "onFailureLogin: ${t.cause.toString()}")
            }

        })
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        var busyDialogFragment = DialogFragment()
        if (isLoading) {
            busyDialogFragment.show(supportFragmentManager)
        } else {
            busyDialogFragment =
                supportFragmentManager.findFragmentByTag(DialogFragment.FRAGMENT_TAG) as DialogFragment
            busyDialogFragment.dismiss()
        }
    }

    private fun showDialog(message: String) {
        DynamicToast.makeError(applicationContext, message, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.TOP, 0, 0)
            show()
        }
    }


}