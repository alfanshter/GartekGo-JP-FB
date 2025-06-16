package com.ptpws.GartekGo.Admin.Pages

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenambahkanSoal(navController: NavController) {
    var masukansoal by remember { mutableStateOf("") }
    var pilihan by remember { mutableStateOf("Pilihan 2") }
    var pilihantambahan by remember { mutableStateOf("") }

    val options = listOf("Pilihan 1", "Pilihan 2")
    val isCustomSelected = pilihan == pilihantambahan
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        Scaffold(topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "SOAL",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = poppinsfamily,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.back),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )

                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xffF5F9FF)
                    )
                )

            }
        }) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(color = Color(0xffF5F9FF))
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 35.dp, end = 32.dp)
                    ) {
                        Text(
                            text = "Masukkan Soal",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))

                        TextField(
                            value = masukansoal,
                            onValueChange = { masukansoal = it },
                            shape = RoundedCornerShape(10.dp),
                            textStyle = TextStyle(
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            placeholder = {
                                Text(
                                    "Ketikkan Soal",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color(0xff8B8B8B)
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(95.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(Modifier.height(17.dp))
                        Text(
                            "Masukkan Pilihan Jawaban (klik jawaban yang benar)",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp, color = Color.Black
                        )
                        Spacer(Modifier.height(4.dp))

                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                                .height(207.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White) // bisa diganti sesuai background
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                options.forEach { option ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .selectable(
                                                selected = (pilihan == option),
                                                onClick = { pilihan = option },
                                                role = Role.RadioButton
                                            )
                                            .padding(vertical = 8.dp)
                                    ) {
                                        RadioButton(
                                            selected = (pilihan == option),
                                            onClick = null,
                                            colors = RadioButtonDefaults.colors(selectedColor = Color.Green)
                                        )
                                        Text(
                                            text = option,
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }

                                // Custom input
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .selectable(
                                            selected = isCustomSelected,
                                            onClick = { pilihan = pilihantambahan },
                                            role = Role.RadioButton
                                        )
                                        .padding(vertical = 8.dp)
                                ) {
                                    RadioButton(
                                        selected = isCustomSelected,
                                        onClick = null,
                                        colors = RadioButtonDefaults.colors(selectedColor = Color.Green)
                                    )
                                    BasicTextField(
                                        value = pilihantambahan,
                                        onValueChange = {
                                            pilihantambahan = it
                                            pilihan = it
                                        },
                                        singleLine = true,
                                        textStyle = TextStyle(
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = poppinsfamily
                                        ),
                                        modifier = Modifier
                                            .padding(start = 8.dp)
                                            .fillMaxWidth()
                                    )
                                }

                                // Disabled "Tambah"
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                ) {
                                    RadioButton(
                                        selected = false,
                                        onClick = null,
                                        enabled = false
                                    )
                                    Text(
                                        text = "Tambah",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            color = Color.Gray,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.height(25.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(
                                onClick = { },
                                modifier = Modifier
                                    .width(139.dp)
                                    .height(60.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xffCA0808)
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "HAPUS",
                                        fontFamily = poppinsfamily,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
                                    )
                                }

                            }

                            Button(
                                onClick = { },
                                modifier = Modifier
                                    .width(139.dp)
                                    .height(60.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xff009A0F)
                                )
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "SIMPAN",
                                        fontFamily = poppinsfamily,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White
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
private fun MenambahkanSoalPreview() {
    MenambahkanSoal(navController = rememberNavController())

}