package com.example.payease

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.payease.auth.FirebaseAuthManager
import com.example.payease.utils.SessionManager

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        val sessionManager = SessionManager(this)
        val logoutButton = findViewById<ImageButton>(R.id.logout)
        val listView= findViewById<ListView>(R.id.listView)
        val employeeNames = listOf("John Doe", "Jane Smith", "Michael Johnson", "Emily Davis", "Daniel Brown")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, employeeNames)
        listView.adapter = adapter

        logoutButton.setOnClickListener {
            FirebaseAuthManager.logout()
            sessionManager.clearSession()
            startActivity(Intent(this, LoginPage::class.java))
            finish()
        }
    }
}