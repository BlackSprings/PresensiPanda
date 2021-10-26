package com.presensi.panda.network

import com.presensi.panda.activities.login.ResponseLogin
import com.presensi.panda.activities.main.ResponseAttendance
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun postLogin(
        @Body userRequest: UserRequest,
    ): Call<ResponseLogin>

    @Headers("Content-Type: application/json")
    @POST("api/test/attendances")
    fun postAttendance(
        @Header("Authorization") token: String,
        @Body attendanceRequest: AttendanceRequest
    ): Call<ResponseAttendance>
}