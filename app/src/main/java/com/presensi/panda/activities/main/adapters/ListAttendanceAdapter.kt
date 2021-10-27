package com.presensi.panda.activities.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.presensi.panda.models.Attendance
import com.presensi.panda.databinding.ItemRowAttendanceBinding

class ListAttendanceAdapter(private val listAttendance: List<Attendance>) : RecyclerView.Adapter<ListAttendanceAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowAttendanceBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (dataDay, dataDate, dataCheckIn, dataCheckOut) = listAttendance[position]
        holder.apply {
            binding.txtDay.text = dataDay
            binding.tvDate.text = dataDate
            binding.tvCheckIn.text = dataCheckIn
            binding.tvCheckOut.text = dataCheckOut
        }
    }

    override fun getItemCount(): Int = listAttendance.size

    class ListViewHolder(var binding: ItemRowAttendanceBinding) : RecyclerView.ViewHolder(binding.root){}

}