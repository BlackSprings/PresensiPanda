package com.presensi.panda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.presensi.panda.databinding.ItemRowAttendanceBinding

class ListAttendanceAdapter(private val listAttendance: ArrayList<Attendance>) : RecyclerView.Adapter<ListAttendanceAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowAttendanceBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val (dataDay, dataDate, dataCheckIn, dataCheckOut) = listAttendance[position]
//        holder.apply {
//
//        }
    }

    override fun getItemCount(): Int = 10

    class ListViewHolder(var binding: ItemRowAttendanceBinding) : RecyclerView.ViewHolder(binding.root){}
}