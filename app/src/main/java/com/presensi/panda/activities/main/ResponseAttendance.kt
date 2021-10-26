package com.presensi.panda.activities.main

import com.google.gson.annotations.SerializedName

data class ResponseAttendance(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("total_data")
	val totalData: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Any? = null,

	@field:SerializedName("count_data")
	val countData: Int? = null,

	@field:SerializedName("code")
	val code: Int? = null,

)

data class Attendance(

	@field:SerializedName("check_in")
	val checkIn: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("check_out")
	val checkOut: String? = null,

	@field:SerializedName("user_operator_id")
	val userOperatorId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("village_name")
	val villageName: String? = null,

	@field:SerializedName("village_mendagri_code")
	val villageMendagriCode: String? = null,

	@field:SerializedName("day")
	val day: List<String?>? = null
)

data class Data(

	@field:SerializedName("attendance_id")
	val attendanceId: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("employee_id")
	val employeeId: Int? = null,

	@field:SerializedName("check_in")
	val checkIn: String? = null,

	@field:SerializedName("check_out")
	val checkOut: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("local_date")
	val localDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("attendance")
	val attendance: Attendance? = null,
)
