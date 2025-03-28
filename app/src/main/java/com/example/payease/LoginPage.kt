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
//import com.example.payease.utils.SessionManager
//
//class LoginPage : AppCompatActivity() {
//    private lateinit var sessionManager: SessionManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_login_page)
//
//        sessionManager = SessionManager(this)
//
//        val emailField = findViewById<EditText>(R.id.email)
//        val passwordField = findViewById<EditText>(R.id.Password)
//        val loginButton = findViewById<Button>(R.id.SignIn)
//        val signUpText = findViewById<TextView>(R.id.SignUp)
//
//        loginButton.setOnClickListener {
//            val email = emailField.text.toString().trim()
//            val password = passwordField.text.toString().trim()
//
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                FirebaseAuthManager.login(email, password) { success, message ->
//                    if (success) {
//                        sessionManager.saveLoginState(true)
//                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this, HomeActivity::class.java))
//                        finish()
//                    } else {
//                        Toast.makeText(this, message ?: "Login failed!", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        signUpText.setOnClickListener {
//            startActivity(Intent(this, SignUp::class.java))
//        }
//    }
//}

package com.example.payease

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.payease.auth.FirebaseAuthManager
import com.example.payease.utils.SessionManager

class LoginPage : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)

        sessionManager = SessionManager(this)
        FirebaseAuthManager.initializeGoogleSignIn(this)

        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.Password)
        val loginButton = findViewById<Button>(R.id.SignIn)
        val googleSignInButton = findViewById<ImageButton>(R.id.googleLogin)
        val signUpText = findViewById<TextView>(R.id.SignUp)

        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuthManager.login(email, password) { success, message ->
                    if (success) {
                        sessionManager.saveLoginState(true)
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, message ?: "Login failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        googleSignInButton.setOnClickListener {
            FirebaseAuthManager.signInWithGoogle(this, RC_SIGN_IN)
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            FirebaseAuthManager.handleGoogleSignInResult(requestCode, data) { success, message ->
                if (success) {
                    sessionManager.saveLoginState(true)
                    Toast.makeText(this, "Google Sign-In successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, message ?: "Google Sign-In failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
