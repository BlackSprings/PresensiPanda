package com.presensi.panda.network

import com.google.gson.annotations.SerializedName

data class AttendanceRequest (
    @SerializedName("employee_id") val employee_id: Int,
    @SerializedName("attendance_id") var attendance_id: String,
    @SerializedName("local_date") var local_date: String,
)