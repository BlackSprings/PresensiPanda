package com.presensi.panda.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee(
    var id: Int?,
    var user_id: Int?,
    var id_number: String?,
    var company_id: String?,
    var company_name: String?,
    var position: String?,
    var pob: String?,
    var dob: String?,
    var gender: String?,
    var address: String?,
):Parcelable
