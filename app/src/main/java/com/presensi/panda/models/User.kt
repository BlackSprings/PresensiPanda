package com.presensi.panda.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username") val username: String,
    @SerializedName("password") var password: String,
)
