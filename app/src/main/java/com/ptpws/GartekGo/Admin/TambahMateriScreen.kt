package com.ptpws.GartekGo.Admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahMateriScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Semester 1", "Semester 2")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {tabTitles.size})
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().background(color = Color(0xffF5F9FF))) {
        Scaffold(
            //topbar start
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "MATERI",
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
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize().background(color = Color(0xffF5F9FF))
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
                    MateriListContent(
                        onTambahClick = {

                        }
                    )
                }

                // LazyColumn berisi semua konten termasuk tombol
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun TambahMateriScreenPreview() {
    TambahMateriScreen(navController = rememberNavController())

}

@Composable
fun MateriListContent(
    onTambahClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 34.dp, end = 34.dp)
            .background(color = Color(0xffF5F9FF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp)
    ) {
        item {
            repeat(3) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDDECFF)),
                    shape = RoundedCornerShape(23.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(start = 22.dp, top = 12.dp)) {
                        Text(
                            text = "Nama Materi",
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsfamily,
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Dibuat 16/08/2025",
                            fontSize = 12.sp,
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // Tombol Tambah Topik
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                border = BorderStroke(2.dp, Color(0xFF2F80ED)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                onClick = onTambahClick
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.tamabahtopik),
                        contentDescription = "Tambah",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tambah Materi",
                        color = Color.Black,
                        fontWeight = FontWeight.Medium,
                        fontFamily = poppinsfamily,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
