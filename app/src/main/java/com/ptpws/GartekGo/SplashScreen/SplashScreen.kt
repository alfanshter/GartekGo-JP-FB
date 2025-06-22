package com.ptpws.GartekGo.SplashScreen

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.AdminActivity
import com.ptpws.GartekGo.Auth.AuthActivity
import com.ptpws.GartekGo.MainActivity
import com.ptpws.GartekGo.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    val db = Firebase.firestore

    // efek yang hanya dijalankan sekali saat SplashScreen tampil
    LaunchedEffect(Unit) {
        delay(3000) // delay 3 detik

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val role = db.collection("users").document(currentUser.uid).get()
            role.addOnSuccessListener {
                val data = it.get("role").toString()
                if (data == "user") {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    Toast.makeText(
                        context,
                        "User Berhasil Login",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    // Tutup Activity Login
                    (context as? AuthActivity)?.finish()
                } else if (data == "guru") {
                    val intent = Intent(context, AdminActivity::class.java)
                    context.startActivity(intent)
                    Toast.makeText(context, "Guru Berhasil Login", Toast.LENGTH_SHORT).show()
                    (context as? Activity)?.finish()


                } else {
                    Toast.makeText(context, "Role tidak dikenali: $role", Toast.LENGTH_SHORT).show()
                }

            }

            role.addOnFailureListener {
                Log.d("Muhib", it.message.toString())
                Toast.makeText(context, "Gagal LOgin", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Belum login → ke AuthActivity
            val intent = Intent(context, AuthActivity::class.java)
            context.startActivity(intent)
        }

        // Tutup SplashActivity agar tidak bisa kembali dengan tombol back
        (context as? Activity)?.finish()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xff0961F5)), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(id = R.drawable.gartekgo),
            contentDescription = null,
            tint = Color.Unspecified
        )

    }


}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()

}