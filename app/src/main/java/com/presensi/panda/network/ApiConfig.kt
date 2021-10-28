package com.presensi.panda.network

import android.content.Context
import android.content.SharedPreferences
import com.presensi.panda.helper.AuthInterceptor
import com.presensi.panda.utils.SharedPrefManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig() {
    companion object {
        fun getApiService(context: Context): ApiService {
            val sharedPrefManager = SharedPrefManager.getInstance(context)
            val server = if(sharedPrefManager.server.isEmpty()) Reference.sipanda else sharedPrefManager.server
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthInterceptor(context))
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(server)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}