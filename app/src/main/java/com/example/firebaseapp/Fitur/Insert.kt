package com.example.firebaseapp.Fitur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.firebaseapp.Mahasiswa
import com.example.firebaseapp.MainActivity
import com.example.firebaseapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Insert : AppCompatActivity() {

    private lateinit var inputNim: TextInputEditText
    private lateinit var inputNama: TextInputEditText
    private lateinit var inputTelepon: TextInputEditText
    private lateinit var insertButton: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        inputNim = findViewById(R.id.inputNimIns)
        inputNama = findViewById(R.id.inputNamaIns)
        inputTelepon = findViewById(R.id.inputTeleponIns)
        insertButton = findViewById(R.id.btnTambah)
        // Mendapatkan referensi database Firebase
        database = FirebaseDatabase.getInstance().getReference("mahasiswa")

        insertButton.setOnClickListener {
            val nim = inputNim.text.toString().trim()
            val nama = inputNama.text.toString().trim()
            val telepon = inputTelepon.text.toString().trim()
            if (nim.isNotEmpty() && nama.isNotEmpty() && telepon.isNotEmpty()) {
                tambahDataMahasiswa(nama, nim, telepon)
            } else {
                // Handling jika field tidak diisi
                // Misalnya dengan menampilkan pesan kesalahan
            }
        }
    }
    private fun tambahDataMahasiswa(nama: String, nim: String, telepon: String) {
        // Generate ID baru untuk mahasiswa
        val mahasiswaId = database.push().key
        // Buat objek Mahasiswa baru
        val mahasiswa = Mahasiswa(nama, nim, telepon)
        // Tambahkan data ke Firebase dengan ID yang baru dibuat
        if (mahasiswaId != null) {
            database.child(mahasiswaId).setValue(mahasiswa).addOnCompleteListener {
                    val intent = Intent(this@Insert, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this@Insert, "Failed to add data to database", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
