//package com.example.payease
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import com.example.payease.auth.FirebaseAuthManager
//
//class SignUp : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_sign_up)
//
//        val emailField = findViewById<EditText>(R.id.SignUpEmail)
//        val passwordField = findViewById<EditText>(R.id.SignUpPassword)
//        val signUpButton = findViewById<Button>(R.id.SignUpButton)
//        val loginText = findViewById<TextView>(R.id.login)
//
//        signUpButton.setOnClickListener {
//            val email = emailField.text.toString().trim()
//            val password = passwordField.text.toString().trim()
//
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                FirebaseAuthManager.signUp(email, password) { success, message ->
//                    if (success) {
//                        Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this, LoginPage::class.java))
//                        finish()
//                    } else {
//                        Toast.makeText(this, message ?: "Sign up failed!", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        loginText.setOnClickListener {
//            startActivity(Intent(this, LoginPage::class.java))
//        }
//    }
//}

package com.example.payease

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.payease.auth.FirebaseAuthManager

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val emailField = findViewById<EditText>(R.id.SignUpEmail)
        val passwordField = findViewById<EditText>(R.id.SignUpPassword)
        val signUpButton = findViewById<Button>(R.id.SignUpButton)
        val googleSignUpButton = findViewById<ImageButton>(R.id.googleSignUp)
        val loginText = findViewById<TextView>(R.id.login)

        signUpButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuthManager.signUp(email, password) { success, message ->
                    if (success) {
                        Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginPage::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, message ?: "Sign up failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        googleSignUpButton.setOnClickListener {
            FirebaseAuthManager.signInWithGoogle(this, 9001)
        }

        loginText.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }
    }
}
