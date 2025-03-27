package com.example.payease

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.payease.auth.FirebaseAuthManager
import com.example.payease.utils.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)

        lifecycleScope.launch {
            delay(2000) // Splash screen delay

            val user = FirebaseAuthManager.getCurrentUser()
            if (user != null && sessionManager.isLoggedIn()) {
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            } else {
                startActivity(Intent(this@MainActivity, LoginPage::class.java))
            }
            finish()
        }
    }
}
//
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
////        window.setFlags(
////            WindowManager.LayoutParams.FLAG_FULLSCREEN,
////            WindowManager.LayoutParams.FLAG_FULLSCREEN
////        )
////        android.os.Handler().postDelayed({
////            val intent= Intent(this,LoginPage::class.java)
////            startActivity(intent)
////            finish()
////        },2000)
//        android.os.Handler().postDelayed({
//            val i = Intent(this@MainActivity, LoginPage::class.java)
//            startActivity(i)
//            finish()
//        },2000)
//    }
//}