package com.presensi.panda.utils

import android.content.Context
import com.presensi.panda.models.User
import com.presensi.panda.network.UserRequest

class SharedPrefManager private constructor(private val context: Context){

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
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

    fun saveUser(user: User) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id",user.id)
        editor.putString("name",user.name)
        editor.putString("username",user.username)
        editor.putString("email",user.email)

        editor.apply()
    }

    fun clear() {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "local_presistent_data"
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