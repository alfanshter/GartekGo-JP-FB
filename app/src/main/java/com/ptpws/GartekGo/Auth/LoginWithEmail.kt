package com.ptpws.GartekGo.Auth

import com.google.firebase.auth.FirebaseAuth

fun loginWithEmail(
    email: String,
    password: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    FirebaseAuth.getInstance()
        .signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                onError(task.exception?.message ?: "Login gagal")
            }
        }
}
