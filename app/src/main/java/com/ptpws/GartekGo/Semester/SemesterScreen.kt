package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SemesterScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Pembelajaran", "Project")


    Scaffold( modifier = Modifier, containerColor = Color(0xffF5F9FF),
        //topbar start
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Semester 1",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = poppinsfamily,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(painter = painterResource(id = R.drawable.back),contentDescription = null, tint = Color.Unspecified)

                        }
                    }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xffF5F9FF))
                )

            }
        }
        //topbar end
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xffF5F9FF))) {
            // TAB ROW DILETAKKAN DI SINI, DI BAWAH TOPBAR
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color.Black,
                divider = {}, // Hilangkan garis bawah
                indicator = { tabPositions ->
                    val currentTab = tabPositions[selectedTabIndex]
                    Box(
                        Modifier
                            .wrapContentSize(Alignment.BottomStart)
                            .offset(x = currentTab.left + (currentTab.width - 42.dp) / 2)
                            .width(42.dp)
                            .height(3.dp)
                            .background(Color(0xFF3D5CFF), shape = MaterialTheme.shapes.small)
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index
                        },
                        selectedContentColor = Color(0xFF1E1E2D),
                        unselectedContentColor = Color(0xFF888888)
                    ) {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = poppinsfamily,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
            }
            // Konten LazyColumn di bawah TabRow
            LazyColumn(
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() - 30.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 34.dp,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xffF5F9FF)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    //card progres start 1

                    Card(
                        modifier = Modifier
                            .padding(end = 34.dp)
                            .fillMaxWidth()
                            .height(129.dp).clickable{ navController.navigate(AppScreen.Home.Semester.Topik.route) }, // <== Tinggi diganti
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFC2D8FF)
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 12.dp,
                                        end = 56.dp,
                                        bottom = 12.dp
                                    ), // disesuaikan agar muat
                                verticalArrangement = Arrangement.spacedBy(6.dp) // jarak antar item
                            ) {
                                Text(
                                    "Topik 1",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp, // sedikit dikecilkan
                                    fontFamily = poppinsfamily,
                                    color = Color.Black
                                )

                                // Progress Bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .height(6.dp)
                                        .background(Color.White, RoundedCornerShape(3.dp))
                                ) {
                                    LinearProgressIndicator(
                                        progress = { 0.7f },
                                        modifier = Modifier.fillMaxSize(),
                                        color = Color(0xFF337DFF),
                                        trackColor = Color.Transparent
                                    )
                                }

                                Column {
                                    Text(
                                        "Tahap",
                                        fontSize = 12.sp, // lebih kecil
                                        fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xff1F1F39)
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Materi ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF337DFF),
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Video ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF337DFF),
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Soal",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            " (Terkunci)",
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Tombol Play tetap di pojok kanan atas
                            IconButton(
                                onClick = { /* aksi */ },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 12.dp, end = 24.dp)
                                    .size(32.dp) // lebih kecil agar proporsional
                                    .background(Color(0xFF337DFF), shape = CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    //card progres end 1
                    Spacer(Modifier.height(12.dp))
                    //card progres start 2

                    Card(
                        modifier = Modifier
                            .padding(end = 34.dp)
                            .fillMaxWidth()
                            .height(129.dp), // <== Tinggi diganti
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xffD2D2D2)
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 12.dp,
                                        end = 56.dp,
                                        bottom = 12.dp
                                    ), // disesuaikan agar muat
                                verticalArrangement = Arrangement.spacedBy(6.dp) // jarak antar item
                            ) {
                                Text(
                                    "Topik 1",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp, // sedikit dikecilkan
                                    fontFamily = poppinsfamily,
                                    color = Color.Black
                                )

                                // Progress Bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .height(6.dp)
                                        .background(Color.White, RoundedCornerShape(3.dp))
                                ) {
                                    LinearProgressIndicator(
                                        progress = { 0f },
                                        modifier = Modifier.fillMaxSize(),
                                        color = Color(0xFF337DFF),
                                        trackColor = Color.Transparent
                                    )
                                }

                                Column {
                                    Text(
                                        "Tahap",
                                        fontSize = 12.sp, // lebih kecil
                                        fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xff1F1F39)
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Materi ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Video ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Soal",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            " (Terkunci)",
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Tombol Play tetap di pojok kanan atas
                            IconButton(
                                onClick = { /* aksi */ },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 12.dp, end = 24.dp)
                                    .size(32.dp) // lebih kecil agar proporsional
                                    .background(Color(0xFFCCCCCC), shape = CircleShape)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.lock),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    //card progres end 2
                    Spacer(Modifier.height(12.dp))
                    //card progres start 3

                    Card(
                        modifier = Modifier
                            .padding(end = 34.dp)
                            .fillMaxWidth()
                            .height(129.dp), // <== Tinggi diganti
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xffD2D2D2)
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 12.dp,
                                        end = 56.dp,
                                        bottom = 12.dp
                                    ), // disesuaikan agar muat
                                verticalArrangement = Arrangement.spacedBy(6.dp) // jarak antar item
                            ) {
                                Text(
                                    "Topik 1",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp, // sedikit dikecilkan
                                    fontFamily = poppinsfamily,
                                    color = Color.Black
                                )

                                // Progress Bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .height(6.dp)
                                        .background(Color.White, RoundedCornerShape(3.dp))
                                ) {
                                    LinearProgressIndicator(
                                        progress = { 0f },
                                        modifier = Modifier.fillMaxSize(),
                                        color = Color(0xFF337DFF),
                                        trackColor = Color.Transparent
                                    )
                                }

                                Column {
                                    Text(
                                        "Tahap",
                                        fontSize = 12.sp, // lebih kecil
                                        fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xff1F1F39)
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Materi ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Video ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Soal",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            " (Terkunci)",
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Tombol Play tetap di pojok kanan atas
                            IconButton(
                                onClick = { /* aksi */ },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 12.dp, end = 24.dp)
                                    .size(32.dp) // lebih kecil agar proporsional
                                    .background(Color(0xFFCCCCCC), shape = CircleShape)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.lock),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    //card progres end 3
                    Spacer(Modifier.height(12.dp))
                    //card progres start 4

                    Card(
                        modifier = Modifier
                            .padding(end = 34.dp)
                            .fillMaxWidth()
                            .height(129.dp), // <== Tinggi diganti
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xffD2D2D2)
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 12.dp,
                                        end = 56.dp,
                                        bottom = 12.dp
                                    ), // disesuaikan agar muat
                                verticalArrangement = Arrangement.spacedBy(6.dp) // jarak antar item
                            ) {
                                Text(
                                    "Topik 1",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp, // sedikit dikecilkan
                                    fontFamily = poppinsfamily,
                                    color = Color.Black
                                )

                                // Progress Bar
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .height(6.dp)
                                        .background(Color.White, RoundedCornerShape(3.dp))
                                ) {
                                    LinearProgressIndicator(
                                        progress = { 0f },
                                        modifier = Modifier.fillMaxSize(),
                                        color = Color(0xFF337DFF),
                                        trackColor = Color.Transparent
                                    )
                                }

                                Column {
                                    Text(
                                        "Tahap",
                                        fontSize = 12.sp, // lebih kecil
                                        fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.Normal,
                                        color = Color(0xff1F1F39)
                                    )

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            "Materi ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Video ",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            "Soal",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontFamily = poppinsfamily
                                        )
                                        Text(
                                            " (Terkunci)",
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            // Tombol Play tetap di pojok kanan atas
                            IconButton(
                                onClick = { /* aksi */ },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 12.dp, end = 24.dp)
                                    .size(32.dp) // lebih kecil agar proporsional
                                    .background(Color(0xFFCCCCCC), shape = CircleShape)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.lock),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }

                    //card progres end 4
                }

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun SemesterScreenPreview() {
    SemesterScreen(navController = rememberNavController())

}