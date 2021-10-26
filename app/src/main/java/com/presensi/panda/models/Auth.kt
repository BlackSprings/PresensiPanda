package com.presensi.panda.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Auth(
    var token: String?,
    var expires_in: Int?,
    var token_type: String?
) : Parcelable
