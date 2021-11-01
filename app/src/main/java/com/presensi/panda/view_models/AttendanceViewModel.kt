package com.presensi.panda.view_models

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.presensi.panda.activities.main.ResponseLogAttendanceEmployee
import com.presensi.panda.models.Attendance
import com.presensi.panda.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class AttendanceViewModel : ViewModel() {

    private val _listLog = MutableLiveData<List<Attendance>>()
    val listReview: LiveData<List<Attendance>> = _listLog

    fun getHistoriesByEmployeeId(context: Context, token: String, employeeId: Int) {
        val client = ApiConfig.getApiService(context).getHistories("Bearer $token", employeeId)
        client.enqueue(object: Callback<ResponseLogAttendanceEmployee>{
            override fun onResponse(
                call: Call<ResponseLogAttendanceEmployee>,
                response: Response<ResponseLogAttendanceEmployee>
            ) {
                val responseBody = response.body()
                val listAttendances = ArrayList<Attendance>()
                for(item in responseBody?.data!!){
                    var attendance = Attendance(day[convertDate(item?.localDate.toString())].toString(),item?.localDate.toString(),item?.checkIn,item?.checkOut)
                    listAttendances.add(attendance)
                }
                _listLog.value = listAttendances
                Log.d(TAG, "onResponseBodyGetHistories: $responseBody")
            }

            override fun onFailure(call: Call<ResponseLogAttendanceEmployee>, t: Throwable) {
                Log.e(TAG, "onFailureGetHistories: ${t.message}")
            }

        })
    }

    private fun convertDate(localDate: String) : Int{
        val localDate = localDate.split("-")
        val day = localDate[0].toInt()
        val currentMonth = month[localDate[1]]
        val year = localDate[2].toInt()

        val calendar = Calendar.getInstance()

        calendar.set(year, currentMonth!!, day)

        return calendar.get(Calendar.DAY_OF_WEEK)
    }

    companion object {
        private const val TAG = "AttendanceViewModel"
        private val day = mapOf(Calendar.MONDAY to "Senin",
            Calendar.TUESDAY to "Selasa",
            Calendar.WEDNESDAY to "Rabu",
            Calendar.THURSDAY to "Kamis",
            Calendar.FRIDAY to "Jum'at",
            Calendar.SATURDAY to "Sabtu",
            Calendar.SUNDAY to "Minggu")

        val month = mapOf("Jan" to 0,
            "Feb" to 1,
            "Mar" to 2,
            "Apr" to 3,
            "May" to 4,
            "Jun" to 5,
            "Jul" to 6,
            "Aug" to 7,
            "Sep" to 8,
            "Oct" to 9,
            "Nov" to 10,
            "Dec" to 11
            )
    }

}