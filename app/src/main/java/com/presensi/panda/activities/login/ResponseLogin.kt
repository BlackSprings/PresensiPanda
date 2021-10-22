package com.presensi.panda.activities.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("data")
	val data: Data? = null,

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

data class Employee(

	@field:SerializedName("pob")
	val pob: String? = null,

	@field:SerializedName("region_mendagri_code")
	val regionMendagriCode: String? = null,

	@field:SerializedName("address")
	val address: Any? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("idnumber")
	val idnumber: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("dob")
	val dob: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("region_name")
	val regionName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: String? = null
)

data class Pivot(

	@field:SerializedName("role_id")
	val roleId: Int? = null,

	@field:SerializedName("model_type")
	val modelType: String? = null,

	@field:SerializedName("model_id")
	val modelId: Int? = null
)

data class RolesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("pivot")
	val pivot: Pivot? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Data(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("api_token")
	val apiToken: Any? = null,

	@field:SerializedName("roles")
	val roles: List<RolesItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("employee")
	val employee: Employee? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
