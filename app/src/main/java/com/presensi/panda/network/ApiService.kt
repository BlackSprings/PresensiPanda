package com.presensi.panda.network

import com.presensi.panda.activities.login.ResponseLogin
import com.presensi.panda.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun postLogin(
        @Body user: User,
    ): Call<ResponseLogin>
}