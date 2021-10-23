package com.presensi.panda.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int,
    var name: String?,
    var username: String?,
    var email: String?,
) : Parcelable
