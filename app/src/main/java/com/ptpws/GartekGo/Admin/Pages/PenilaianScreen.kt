package com.ptpws.GartekGo.Admin.Pages

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenilaianScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Sudah Dinilai", "Belum Dinilai")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()

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
                                text = "Penilaian",
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
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = Color(0xffF5F9FF))
            ) {
                // Tab Row
                // TAB ROW DILETAKKAN DI SINI, DI BAWAH TOPBAR
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    contentColor = Color.Black,
                    divider = {}, // hilangkan garis bawah
                    indicator = { tabPositions ->
                        val currentTab = tabPositions[pagerState.currentPage]
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
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            selectedContentColor = Color(0xFF1E1E2D),
                            unselectedContentColor = Color(0xFF888888)
                        ) {
                            Text(
                                text = title,
                                fontSize = 16.sp, fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    PenilaianContent()

                }

                // LazyColumn berisi semua konten termasuk tombol
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun PenilaianScreenPreview() {
    PenilaianScreen(navController = rememberNavController())

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PenilaianContent() {
    val chipLabels =
        listOf("Chip Filter", "Chip Filter", "Chip Filter", "Chip Filter", "Chip Filter")
    var selectedChipgambarIndex by remember { mutableStateOf(1) } // index chip yang aktif
    val context = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        FlowRow(
            modifier = Modifier.padding(16.dp).background(color = Color(0xffF5F9FF)),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            chipLabels.forEachIndexed { index, label ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selectedChipgambarIndex == index) Color(0xFF2962FF) else Color.Gray
                    ),
                    color = Color.Transparent,
                    modifier = Modifier
                        .height(23.dp)
                        .clickable { selectedChipgambarIndex = index }
                ) {
                    Text(
                        text = label,
                        fontWeight = if (selectedChipgambarIndex == index) FontWeight.Bold else FontWeight.Normal,
                        fontFamily = poppinsfamily,
                        fontSize = 11.sp,
                        color = if (selectedChipgambarIndex == index) Color(0xFF2962FF) else Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }


        // LazyColumn content di bawahnya
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))) {
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nama :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Cristiano Ronaldo",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Kelas :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )

                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "7 TKJ 1",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "No. Absen :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "7",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nilai :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "95",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -27.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Lihat Gambar",
                                color = Color(0xFF167F71),
                                fontFamily = poppinsfamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    Toast.makeText(
                                        context,
                                        "Lihat Gambar diklik",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )

                        }


                    }
                }

                Spacer(Modifier.height(7.dp))

                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()) {
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nama :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Lamine Yamal",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Kelas :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )

                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "10 TKJ 2",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "No. Absen :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "19",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nilai :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "45",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -27.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Lihat Gambar",
                                color = Color(0xFF167F71),
                                fontFamily = poppinsfamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    Toast.makeText(
                                        context,
                                        "Lihat Gambar diklik",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )

                        }


                    }
                }

                Spacer(Modifier.height(7.dp))

                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()) {
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nama :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = " Ansu Fati",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Kelas :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )

                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = " 10 TKJ 3",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "No. Absen :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "10",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nilai :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = " 10",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -27.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Lihat Gambar",
                                color = Color(0xFF167F71),
                                fontFamily = poppinsfamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    Toast.makeText(
                                        context,
                                        "Lihat Gambar diklik",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )

                        }


                    }
                }

                Spacer(Modifier.height(7.dp))

                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()) {
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nama :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Lionel Messi",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Kelas :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )

                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "10 TKJ 1",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "No. Absen :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "10",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Nilai :",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "100",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = -27.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Lihat Gambar",
                                color = Color(0xFF167F71),
                                fontFamily = poppinsfamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    Toast.makeText(
                                        context,
                                        "Lihat Gambar diklik",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )

                        }


                    }
                }
            }
        }

    }

}


