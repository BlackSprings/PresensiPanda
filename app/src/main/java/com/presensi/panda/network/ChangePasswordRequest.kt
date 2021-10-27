package com.presensi.panda.network

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest (
    @SerializedName("old_password") val old_password: String,
    @SerializedName("password") var password: String,
    @SerializedName("password_confirmation") val password_confirmation: String,
)