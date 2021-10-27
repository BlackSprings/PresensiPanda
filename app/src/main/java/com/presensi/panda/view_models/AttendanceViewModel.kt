package com.presensi.panda.view_models

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.presensi.panda.activities.main.ResponseLogAttendanceEmployee
import com.presensi.panda.models.Attendance
import com.presensi.panda.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                    var attendance = Attendance(item?.localDate.toString(),item?.localDate.toString(),item?.checkIn,item?.checkOut)
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

    companion object {
        private const val TAG = "AttendanceViewModel"
    }

}