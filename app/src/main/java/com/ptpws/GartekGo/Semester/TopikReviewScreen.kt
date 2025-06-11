package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopikReviewScreen(modifier: Modifier = Modifier) {
    var selectedOption by remember { mutableStateOf("") }

    val options = listOf("Pilihan 1", "Pilihan 2", "Pilihan 3", "Pilihan 4")

    Scaffold(modifier = Modifier, containerColor = Color(0xffF5F9FF), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Topik 1 : SOAL",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    fontFamily = poppinsfamily,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* back */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xffF5F9FF) // TopAppBar background
            )
        )

    }
    ) { innerPadding ->
        Spacer(Modifier.height(40.dp))
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (content, button) = createRefs()

            // LazyColumn: isi pertanyaan dan pilihan
            LazyColumn(
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(button.top)
                        height = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 24.dp).background(color = Color(0xffF5F9FF))
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "10/10",
                        fontSize = 16.sp,
                        fontFamily = jostfamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                item {
                    Text(
                        text = "Soal eifjwejgtjfg nfijwheifg ndsiujgnjisedngji knrgoerngoiunergoienrokgrok",
                        fontSize = 16.sp,
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(options.size) { index ->
                    val option = options[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (option == selectedOption),
                                onClick = { selectedOption = option }
                            )
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = { selectedOption = option },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF007AFF)
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = option,
                            fontFamily = poppinsfamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Tombol tetap di bawah layar
            Button(
                onClick = { /* TODO: aksi */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF009A0F),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {

                    // Card + Icon di kiri (awal)
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                            .size(48.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, // ganti dengan ikon yang kamu mau
                                contentDescription = "Kembali",
                                tint = Color(0xFF009A0F),
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }

                    // Teks di tengah
                    Text(
                        text = "SELANJUTNYA",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsfamily,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    // Card + Icon di kanan (akhir)
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .size(48.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Masuk",
                                tint = Color(0xFF009A0F),
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
            }


        }

    }

}

@Preview(showBackground = true)
@Composable
private fun TopikReviewScreenPreview() {
    TopikReviewScreen()

}