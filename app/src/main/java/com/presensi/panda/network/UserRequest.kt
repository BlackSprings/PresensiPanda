package com.presensi.panda.network

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") var password: String,
)
