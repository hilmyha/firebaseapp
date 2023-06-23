package com.example.firebaseapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MahasiswaAdapter(private val userList: ArrayList<Mahasiswa>) : RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.nama.text = currentItem.nama
        holder.nim.text = currentItem.nim
        holder.telp.text = currentItem.telp
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.namaTextView)
        val nim: TextView = itemView.findViewById(R.id.nimTextView)
        val telp: TextView = itemView.findViewById(R.id.telpTextView)
    }
}