package com.example.payease

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//        android.os.Handler().postDelayed({
//            val intent= Intent(this,LoginPage::class.java)
//            startActivity(intent)
//            finish()
//        },2000)
        android.os.Handler().postDelayed({
            val i = Intent(this@MainActivity, LoginPage::class.java)
            startActivity(i)
            finish()
        },2000)
    }
}