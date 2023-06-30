package com.example.firebaseapp.Fitur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebaseapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Edit : AppCompatActivity() {

    private lateinit var inputNama: EditText
    private lateinit var inputNim: EditText
    private lateinit var inputTelepon: EditText
    private lateinit var btnUpdate: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val nim = intent.getStringExtra("nim")!!
        val nama = intent.getStringExtra("nama")!!
        val telp = intent.getStringExtra("telp")!!
        inputNama = findViewById(R.id.inputNama)
        inputNim = findViewById(R.id.inputNim)
        inputTelepon = findViewById(R.id.inputTelepon)
        btnUpdate = findViewById(R.id.btnUpdate)
        database = FirebaseDatabase.getInstance().getReference("mahasiswa")

        // Set the existing data to EditText fields
        inputNama.setText(nama)
        inputNim.setText(nim)
        inputTelepon.setText(telp)
        btnUpdate.setOnClickListener {
            val updatedNama = inputNama.text.toString().trim()
            val updatedTelepon = inputTelepon.text.toString().trim()
            btnUpdate.setOnClickListener {
                val updatedNama = inputNama.text.toString().trim()
                val updatedTelepon =
                    inputTelepon.text.toString().trim()
                if (updatedNama.isNotEmpty() &&
                    updatedTelepon.isNotEmpty()) {
                    // Update the data in Firebase
                    val updatedData = HashMap<String, Any>()
                    updatedData["nama"] = updatedNama
                    updatedData["nim"] = nim
                    updatedData["telp"] = updatedTelepon
                    database.child(nim).updateChildren(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(this@Edit, "Data updated successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this@Edit, "Failed to update data", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this@Edit, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}