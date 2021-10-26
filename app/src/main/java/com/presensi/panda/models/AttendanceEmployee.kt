package com.presensi.panda.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AttendanceEmployee(
    var id: Int?,
    var local_date: String?,
    var check_in: String?,
    var check_out: String?,
    var status: String?,
    var attendance_id: String?,
    var employee_id: Int?,
) : Parcelable
