package com.presensi.panda.activities.main

import com.google.gson.annotations.SerializedName

data class ResponseLogAttendanceEmployee(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class DataItem(

	@field:SerializedName("attendance_id")
	val attendanceId: Int? = null,

	@field:SerializedName("check_out")
	val checkOut: String? = null,

	@field:SerializedName("employee_id")
	val employeeId: Int? = null,

	@field:SerializedName("check_in")
	val checkIn: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("attendance")
	val attendance: Attendance? = null,

	@field:SerializedName("local_date")
	val localDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
