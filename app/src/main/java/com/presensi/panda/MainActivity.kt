package com.presensi.panda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.presensi.panda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val listAttendance = ArrayList<Attendance>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.rvAttendances.setHasFixedSize(true)
        listAttendance.addAll(listAttendances)
        showRecyclerList()
    }

    private val listAttendances: ArrayList<Attendance>
        get() {
            val listData = ArrayList<Attendance>()
            for(i in 1..10){
                var attendance = Attendance("Sabtu","13 Oktober 2021","08:00","12:00")
                listData.add(attendance)
            }
            return listData
        }

    private fun showRecyclerList() {
        binding.rvAttendances.layoutManager = LinearLayoutManager(this)
        val listAttendanceAdapter = ListAttendanceAdapter(listAttendance)
        binding.rvAttendances.adapter = listAttendanceAdapter
    }
}