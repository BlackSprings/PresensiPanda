package com.presensi.panda.network

import com.google.gson.annotations.SerializedName

data class ResponseRefreshToken(

	@field:SerializedName("data")
	val data: List<Any?>? = null,

	@field:SerializedName("total_data")
	val totalData: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("expires_in")
	val expiresIn: Int? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("count_data")
	val countData: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)
