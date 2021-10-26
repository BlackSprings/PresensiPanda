package com.presensi.panda.utils

import android.content.Context
import com.presensi.panda.models.*

class SharedPrefManager private constructor(private val context: Context){

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val isAttendance: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_ATTENDANCE, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val message: String
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_ATTENDANCE, Context.MODE_PRIVATE)
            return sharedPreferences.getString("message",null).toString()
        }

    val user: User
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("email",null)
            )
        }

    val auth: Auth
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_AUTH, Context.MODE_PRIVATE)
            return Auth(
                sharedPreferences.getString("token",null),
                sharedPreferences.getInt("expires_in",-1),
                sharedPreferences.getString("token_type",null)
            )
        }

    val employee: Employee
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_EMPLOYEE, Context.MODE_PRIVATE)
            return Employee(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getInt("user_id",-1),
                sharedPreferences.getString("id_number",null),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("company_id",null),
                sharedPreferences.getString("company_name",null),
                sharedPreferences.getString("position",null),
                sharedPreferences.getString("pob",null),
                sharedPreferences.getString("dob",null),
                sharedPreferences.getString("gender",null),
                sharedPreferences.getString("address",null),
            )
        }

    val attendanceEmployee: AttendanceEmployee
        get() {
            val sharedPrefManager = context.getSharedPreferences(SHARED_PREF_ATTENDANCE, Context.MODE_PRIVATE)
            return AttendanceEmployee(
                sharedPrefManager.getInt("id",-1),
                sharedPrefManager.getString("local_date",null),
                sharedPrefManager.getString("check_in",null),
                sharedPrefManager.getString("check_out",null),
                sharedPrefManager.getString("status",null),
                sharedPrefManager.getString("attendance_id",null),
                sharedPrefManager.getInt("employee_id",-1)
            )
        }

    fun saveUser(user: User) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id",user.id)
        editor.putString("name",user.name)
        editor.putString("username",user.username)
        editor.putString("email",user.email)

        editor.apply()
    }

    fun saveAuth(auth: Auth) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_AUTH, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("token",auth.token)
        editor.putInt("expires_in", auth.expires_in!!)
        editor.putString("token_type",auth.token_type)

        editor.apply()
    }

    fun saveEmployee(employee: Employee){
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_EMPLOYEE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", employee.id!!)
        editor.putInt("user_id",employee.user_id!!)
        editor.putString("name",employee.name)
        editor.putString("id_number",employee.id_number)
        editor.putString("company_id",employee.company_id)
        editor.putString("company_name",employee.company_name)
        editor.putString("position",employee.position)
        editor.putString("pob", employee.pob)
        editor.putString("dob", employee.dob)
        editor.putString("gender", employee.gender)
        editor.putString("address", employee.address)

        editor.apply()
    }

    fun saveAttendanceEmployee(attendanceEmployee: AttendanceEmployee){
        val sharedPrefManager = context.getSharedPreferences(SHARED_PREF_ATTENDANCE, Context.MODE_PRIVATE)
        val editor = sharedPrefManager.edit()

        editor.putString("local_date", attendanceEmployee.local_date)
        editor.putString("check_in", attendanceEmployee.check_in)
        editor.putString("check_out", attendanceEmployee.check_out)
        editor.putString("status", attendanceEmployee.status)
        editor.putString("attendance_id", attendanceEmployee.attendance_id)
        editor.putInt("employee_id",attendanceEmployee.employee_id!!)
        editor.putInt("id",attendanceEmployee.id!!)

        editor.apply()
    }

    fun saveMessage(message: String) {
        val sharedPrefManager = context.getSharedPreferences(SHARED_PREF_ATTENDANCE, Context.MODE_PRIVATE)
        val editor = sharedPrefManager.edit()

        editor.putString("message",message)
        editor.apply()
    }

    fun clear(SHARED_KEY : String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        val SHARED_PREF_NAME = "local_user_presistent_data"
        val SHARED_PREF_AUTH = "local_auth_presistent_data"
        val SHARED_PREF_EMPLOYEE = "local_employee_presistent_data"
        val SHARED_PREF_ATTENDANCE = "lcoal_attendance_employee_presistent_data"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager {
            if(mInstance == null){
                mInstance = SharedPrefManager(context)
            }
            return mInstance as SharedPrefManager
        }
    }

}