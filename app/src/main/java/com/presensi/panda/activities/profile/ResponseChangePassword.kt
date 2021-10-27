package com.presensi.panda.activities.profile

import com.google.gson.annotations.SerializedName

data class ResponseChangePassword(

	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("total_data")
	val totalData: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("count_data")
	val countData: Int? = null,

	@field:SerializedName("errors")
	val errors: String? = null
)
