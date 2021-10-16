package com.presensi.panda

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Attendance(
    var day: String,
    var date: String,
    var check_in: String?,
    var check_out: String?,
) : Parcelable
