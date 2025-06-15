package com.ptpws.GartekGo.Admin.Pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoalListScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Semester 1", "Semester 2")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()

    var namaSoal by remember { mutableStateOf("") }
    var selectedTopik by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val topikList = listOf("Topik 1", "Topik 2", "Topik 3")
    var jumlahsoal by remember { mutableStateOf("") }

    val radioOptions = listOf("Leonel Messi", "Ronaldo", "Lamine Yamal", "Bellingham")
    var selectedOption1 by remember { mutableStateOf(radioOptions[1]) }
    var selectedOption2 by remember { mutableStateOf(radioOptions[1]) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        Scaffold(
            //topbar start
            topBar = {
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
            },
        ) { innerPadding ->
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
                            .background(Color(0xFFF5F9FF))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { /* Draft */ },
                                modifier = Modifier
                                    .width(139.dp)
                                    .height(40.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xff5C5C5C
                                    )
                                )
                            ) {
                                Text(
                                    "DRAFT",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp, color = Color.White
                                )
                            }
                            Button(
                                onClick = { /* Draft */ },
                                modifier = Modifier
                                    .width(139.dp)
                                    .height(40.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xff009A0F
                                    )
                                )
                            ) {
                                Text(
                                    "PUBLISH",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp, color = Color.White
                                )
                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Jumlah Soal",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xff020202),
                                fontSize = 12.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            TextField(
                                value = jumlahsoal,
                                onValueChange = { jumlahsoal = it },
                                shape = RoundedCornerShape(10.dp),
                                textStyle = TextStyle(
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )

                        }


                        TextField(
                            value = namaSoal,
                            onValueChange = { namaSoal = it },
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    "Nama Soal",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            },
                            textStyle = TextStyle(
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        // Dropdown
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = selectedTopik,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = {
                                    Text(
                                        "Pilih Topik",
                                        fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                },
                                textStyle = TextStyle(
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.tambahdrop), // panah kiri
                                        contentDescription = null, tint = Color.Unspecified,
                                        modifier = Modifier
                                            .rotate(if (expanded) 90f else 0f) // rotasi ke bawah saat expanded
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                            ) {
                                topikList.forEach { topik ->
                                    DropdownMenuItem(
                                        text = { Text(topik,
                                                fontFamily = poppinsfamily,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 16.sp,
                                                color = Color.Black
                                            ) },
                                        onClick = {
                                            selectedTopik = topik
                                            expanded = false
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }


                        // Soal Card
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "1. Siapa Pemain Sepak Bola Terbaik Sepanjang Masa...",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = poppinsfamily,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                radioOptions.forEach { text ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        RadioButton(
                                            selected = (text == selectedOption1),
                                            onClick = { selectedOption1 = text },
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = Color(0xFF009F3A)
                                            )
                                        )
                                        Text(
                                            text, fontWeight = FontWeight.SemiBold,
                                            fontFamily = poppinsfamily,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { /* Edit */ },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(
                                                0xFF009F3A
                                            )
                                        ),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.edit),
                                            contentDescription = "Edit",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            "EDIT", color = Color.White, fontFamily = jostfamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                    }
                                    Button(
                                        onClick = { /* Hapus */ },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.hapus),
                                            contentDescription = "Hapus",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            "HAPUS", color = Color.White, fontFamily = jostfamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(19.dp))
                        // Soal Card
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "2. Siapa Pemain Sepak Bola Terbaik Sepanjang Masa...",
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = poppinsfamily,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                radioOptions.forEach { text ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                    ) {
                                        RadioButton(
                                            selected = (text == selectedOption2),
                                            onClick = { selectedOption2 = text },
                                            colors = RadioButtonDefaults.colors(
                                                selectedColor = Color(0xFF009F3A)
                                            )
                                        )
                                        Text(
                                            text, fontWeight = FontWeight.SemiBold,
                                            fontFamily = poppinsfamily,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { /* Edit */ },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(
                                                0xFF009F3A
                                            )
                                        ),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.edit),
                                            contentDescription = "Edit",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            "EDIT", color = Color.White, fontFamily = jostfamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                    }
                                    Button(
                                        onClick = { /* Hapus */ },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.hapus),
                                            contentDescription = "Hapus",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            "HAPUS",
                                            color = Color.White,
                                            fontFamily = jostfamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                        }

                        // Tombol Tambah Soal Pakai ConstraintLayout
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(96.dp),
                                border = BorderStroke(2.dp, Color(0xFF2F80ED)),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                onClick = { }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.tambahh),
                                        contentDescription = "Tambah",
                                        tint = Color.Unspecified
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
private fun SoalListScreenPreview() {
    SoalListScreen(navController = rememberNavController())

}