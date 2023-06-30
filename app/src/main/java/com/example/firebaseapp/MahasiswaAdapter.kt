package com.example.firebaseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MahasiswaAdapter(
    private val userList: ArrayList<Mahasiswa>,
    private val context: Context,
    private val editClickListener: (Mahasiswa) -> Unit,
    private val deleteClickListener: (Mahasiswa) -> Unit
) : RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder>() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("mahasiswa")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.nama.text = currentItem.nama
        holder.nim.text = "Nim: ${currentItem.nim}" // Ubah pengisian kolom nim
        holder.telp.text = currentItem.telp
        holder.editButton.setOnClickListener {
            editClickListener.invoke(currentItem)
        }
        holder.deleteButton.setOnClickListener {
            deleteClickListener.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.namaTextView)
        val nim: TextView = itemView.findViewById(R.id.nimTextView)
        val telp: TextView = itemView.findViewById(R.id.telpTextView)
        val editButton: ImageButton = itemView.findViewById(R.id.btnEdit)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnDellete)
        init {
            editButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val mahasiswa = userList[position]
                    editClickListener.invoke(mahasiswa)

                }
                deleteButton.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val mahasiswa = userList[position]
                        deleteClickListener.invoke(mahasiswa)
                    }
                }
            }
        }
    }
}