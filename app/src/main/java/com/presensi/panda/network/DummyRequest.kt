package com.presensi.panda.network

import com.google.gson.annotations.SerializedName

data class DummyRequest(
    @SerializedName("dummy") var dummy: String,
)
