package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopikEditScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Topik 1 :\nBelajar Membaca",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back", tint = Color.Unspecified
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF5F9FF)
                )
            )
        },
        containerColor = Color(0xFFF5F9FF)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (cardRef, kirimRef) = createRefs()

                // Card putih
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(cardRef) {
                            top.linkTo(parent.top, margin = 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(kirimRef.top, margin = 16.dp)
                        },
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Gambar
                        Image(
                            painter = painterResource(id = R.drawable.gambartugas), // Ganti sesuai gambar kamu
                            contentDescription = null,
                            modifier = Modifier
                                .size(220.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Tombol Edit
                        Button(
                            onClick = { /* TODO */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A651)),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.edit), contentDescription = null, tint = Color.Unspecified)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("EDIT", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Tombol Hapus
                        Button(
                            onClick = { /* TODO */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.hapus), tint = Color.Unspecified, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("HAPUS", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                        }
                    }
                }

                val ( button) = createRefs()
                // Tombol tetap di bawah layar
                Button(
                    onClick = { /* TODO: aksi */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0961F5), // Warna biru
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .constrainAs(button) {
                            bottom.linkTo(parent.bottom, margin = 24.dp) // agar tidak mentok
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Teks di tengah
                        Text(
                            text = "KIRIM",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = poppinsfamily,
                            modifier = Modifier.align(Alignment.Center)
                        )

                        // Card bulat isi ikon panah di kanan
                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp) // jarak dari tepi kanan
                                .size(48.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Masuk",
                                    tint = Color(0xFF0961F5),
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


@Preview(showBackground = true)
@Composable
private fun TopikEditScreenPreview() {
    TopikEditScreen()

}


