package com.presensi.panda.network

import com.presensi.panda.activities.login.ResponseLogin
import com.presensi.panda.activities.main.ResponseAttendance
import com.presensi.panda.activities.main.ResponseLogAttendanceEmployee
import retrofit2.Call
import retrofit2.http.*

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

    @Headers("Content-Type: application/json")
    @GET("api/test/attendance_employees/{id}/history")
    fun getHistories(
        @Header("Authorization") token: String,
        @Path("id") employee_id: Int,
    ): Call<ResponseLogAttendanceEmployee>


}