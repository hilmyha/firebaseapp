package com.example.firebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapp.Fitur.Edit
import com.example.firebaseapp.Fitur.Insert
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var adapter: MahasiswaAdapter
    private lateinit var userList: ArrayList<Mahasiswa>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val insertButton: Button = findViewById(R.id.insertButton)
        insertButton.setOnClickListener {
            insertMahasiswa()
        }

        // Inisialisasi RecyclerView
        userRecyclerView = findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi ArrayList dan Adapter
        userList = ArrayList()
        adapter = MahasiswaAdapter(userList, this, ::editMahasiswa, ::deleteMahasiswa)
        userRecyclerView.adapter = adapter

        // Mendapatkan referensi database Firebase
        database = FirebaseDatabase.getInstance().getReference("mahasiswa")

        // Mendapatkan data dari Firebase
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Hapus data sebelumnya dari userList
                userList.clear()

                // Loop melalui setiap item data dan tambahkan ke userList
                for (snapshot in dataSnapshot.children) {
                    val mahasiswa = snapshot.getValue(Mahasiswa::class.java)
                    mahasiswa?.let {
                        userList.add(it)
                    }
                }

                // Memperbarui RecyclerView setelah mendapatkan data baru
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Penanganan kesalahan saat mengakses Firebase
            }
        })
    }

    private fun deleteMahasiswa(mahasiswa: Mahasiswa) {
        // Delete the mahasiswa data from Firebase
        val userId = mahasiswa.nim
        userId?.let {
            database.child(it).removeValue()
        }
    }

    private fun editMahasiswa(mahasiswa: Mahasiswa) {
        // Open the edit activity or perform the edit operation as needed
        val intent = Intent(this, Edit::class.java)
        intent.putExtra("nama", mahasiswa.nama)
        intent.putExtra("nim", mahasiswa.nim)
        intent.putExtra("telp", mahasiswa.telp)
        startActivity(intent)

    }

    private fun insertMahasiswa() {
        val intent = Intent(this, Insert::class.java)
        startActivity(intent)
    }
}