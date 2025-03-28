//package com.example.payease.auth
//
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//
//object FirebaseAuthManager {
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//
//    fun signUp(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                onComplete(task.isSuccessful, task.exception?.message)
//            }
//    }
//
//    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                onComplete(task.isSuccessful, task.exception?.message)
//            }
//    }
//
//    fun getCurrentUser(): FirebaseUser? {
//        return auth.currentUser
//    }
//
//    fun logout() {
//        auth.signOut()
//    }
//}
//
package com.example.payease.auth

import android.app.Activity
import android.content.Intent
import com.example.payease.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

object FirebaseAuthManager {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    fun signUp(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful, task.exception?.message)
            }
    }

    fun login(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful, task.exception?.message)
            }
    }

    fun initializeGoogleSignIn(activity: Activity) {
        oneTapClient = Identity.getSignInClient(activity)
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(activity.getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }

    fun signInWithGoogle(activity: Activity, requestCode: Int) {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                activity.startIntentSenderForResult(
                    result.pendingIntent.intentSender, requestCode, null, 0, 0, 0, null
                )
            }
            .addOnFailureListener {
                // Handle failure
            }
    }

    fun handleGoogleSignInResult(
        requestCode: Int,
        data: Intent?,
        onComplete: (Boolean, String?) -> Unit
    ) {
        if (requestCode == 9001) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { task ->
                            onComplete(task.isSuccessful, task.exception?.message)
                        }
                }
            } catch (e: ApiException) {
                onComplete(false, e.message)
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun logout() {
        auth.signOut()
    }
}
