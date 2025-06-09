package com.ptpws.GartekGo.Auth

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.mulishfamily
import com.ptpws.GartekGo.R


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
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
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email Icon")
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
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                    },
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, contentDescription = null)
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
                    onClick = { /* TODO: Login action */ },
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
    LoginScreen()

}