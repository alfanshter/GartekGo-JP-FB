package com.ptpws.GartekGo.Auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.AdminActivity
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.mulishfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.MainActivity
import com.ptpws.GartekGo.R


@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var context = LocalContext.current

    //panggil inisialisasi Firebase Auth
    var database = FirebaseAuth.getInstance()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        item {
            Spacer(Modifier.height(57.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.illustration_2),
                    contentDescription = null,
                    modifier = Modifier.size(260.dp)
                )

            }

            Spacer(Modifier.height(36.dp))

            Text(
                text = "Masuk",
                fontFamily = jostfamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xff202244),
                modifier = Modifier.padding(start = 34.dp)
            )

            Text(
                text = "Masuk Untuk Mengakses Pembelajaran",
                fontFamily = mulishfamily,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp, color = Color(0xff545454),
                modifier = Modifier.padding(start = 34.dp)
            )
            Spacer(Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 34.dp, end = 34.dp)
            ) {
                // Email TextField
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.Black) }, textStyle = TextStyle(
                        fontFamily = mulishfamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black),
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = Color(0xff545454))
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(360.dp)
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent

                    )
                )

                Spacer(Modifier.height(20.dp))

                // Password TextField
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.Black) }, textStyle = TextStyle(
                        fontFamily = mulishfamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.Black),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = Color(0xff545454))
                    },
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null, tint = Color(0xff545454))
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(360.dp)
                        .padding(bottom = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,

                        )


                )

                // Remember Me & Forgot Password
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it }
                        )
                        Text(
                            "Remember Me", fontSize = 13.sp,
                            fontFamily = mulishfamily,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xff545454)
                        )
                    }

                    TextButton(onClick = { /* TODO */ }) {
                        Text(
                            "Forgot Password?",
                            fontSize = 13.sp,
                            fontFamily = mulishfamily,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xff545454)
                        )
                    }
                }
                Spacer(Modifier.height(45.dp))

                // Masuk Button
                Button(
                    onClick = {
//                        loginWithEmail(email,password, onSuccess = {
//
//                    }, onError = {  error ->
//                        errorMessage = error })
                        Log.d("muhib", "${email} $password ")


                        var loginWithFirebase = database.signInWithEmailAndPassword(email, password)
                        val db = Firebase.firestore
                        //read data
                        loginWithFirebase.addOnSuccessListener {
                            val role = db.collection("users").document(it.user!!.uid).get()
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
//
                        }



                        loginWithFirebase.addOnFailureListener {
                            Log.d("Muhib ", "${it.message}")
                            Toast.makeText(context, "Gagal Login", Toast.LENGTH_SHORT).show()

                        }


                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0057FF))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        // Teks di tengah
                        Text(
                            text = "Masuk",
                            color = Color.White,
                            fontFamily = jostfamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        errorMessage?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = it, color = Color.Red)
                        }

                        // Icon di kanan dalam lingkaran
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp) // kasih padding biar tidak terlalu mepet
                                .size(48.dp)
                        ) {
                            Card(
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "Masuk",
                                        tint = Color(0xFF0057FF),
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }


    }

}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())

}