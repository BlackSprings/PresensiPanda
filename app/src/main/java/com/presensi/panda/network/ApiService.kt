package com.presensi.panda.network

import com.presensi.panda.activities.login.ResponseLogin
import com.presensi.panda.activities.main.ResponseAttendance
import com.presensi.panda.activities.main.ResponseLogAttendanceEmployee
import com.presensi.panda.activities.profile.ResponseChangePassword
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun postLogin(
        @Body userRequest: UserRequest,
    ): Call<ResponseLogin>

    @Headers("Content-Type: application/json")
    @POST("api/v2/attendances")
    fun postAttendance(
        @Header("Authorization") token: String,
        @Body attendanceRequest: AttendanceRequest
    ): Call<ResponseAttendance>

    @Headers("Content-Type: application/json")
    @GET("api/v2/attendance_employees/{id}/history")
    fun getHistories(
        @Header("Authorization") token: String,
        @Path("id") employee_id: Int,
    ): Call<ResponseLogAttendanceEmployee>

    @Headers("Content-Type: application/json")
    @POST("api/v2/users/{id}/change_password")
    fun postChangePassword(
        @Header("Authorization") token: String,
        @Path("id") user_id: Int,
        @Body changePasswordRequest: ChangePasswordRequest
    ): Call<ResponseChangePassword>

}