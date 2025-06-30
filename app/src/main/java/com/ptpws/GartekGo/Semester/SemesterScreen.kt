package com.ptpws.GartekGo.Semester

import TopikModel
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SemesterScreen(
    navController: NavController,
    onTambahClick: () -> Unit,
    pilihan: Int,
    outerPadding: PaddingValues = PaddingValues()
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Pembelajaran", "Project")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabTitles.size })
    val coroutineScope = rememberCoroutineScope()




    when (pilihan) {
        1 -> {
            SemesterListContent(
                navController = navController,
                onTambahClick = onTambahClick,
                pilihan
            )
        }

        2 -> {
            ProjectScreen(navController)
        }


    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        Scaffold(
            modifier = Modifier, containerColor = Color(0xffF5F9FF),
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
                            IconButton(onClick = {
                                if (navController.previousBackStackEntry != null) {
                                    navController.popBackStack()
                                }
                            }) {
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
            }
            //topbar end
        ) { innerPadding ->
            val combinedPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = outerPadding.calculateBottomPadding()
            )
            Column(
                modifier = Modifier
                    .padding(combinedPadding)
                    .fillMaxSize()
                    .background(Color(0xffF5F9FF))
            ) {
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
                    when (page) {
                        0 -> {
                            SemesterListContent(
                                pilihan = 1, // Kalau mau tetap kirim pilihan
                                navController = navController,
                                onTambahClick = onTambahClick
                            )
                        }

                        1 -> {
                            ProjectScreen(navController)
                        }
                    }
                }
                // Konten LazyColumn di bawah TabRow
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun SemesterScreenPreview() {
    SemesterScreen(navController = rememberNavController(), onTambahClick = {}, pilihan = 0)

}

@Composable
fun SemesterListContent(navController: NavController, onTambahClick: () -> Unit, pilihan: Int) {
    var id by remember { mutableStateOf("") }
    var context = LocalContext.current
    var db = Firebase.firestore
    val listData = remember { mutableStateListOf<TopikModel>() }
    LaunchedEffect(Unit) {
        val getdata = db.collection("topik")
            .orderBy("nomor")
            .get()
        getdata.addOnSuccessListener { data ->
            listData.clear()


            for (datas in data.documents) {
                val topik = datas.toObject(TopikModel::class.java)
                if (topik != null) {
                    listData.add(topik.copy(id = datas.id))
                }

            }

            val uid = FirebaseAuth.getInstance().uid
            val db = Firebase.firestore

            db.collection("users").document(uid.toString())
                .collection("topik")
                .get()
                .addOnSuccessListener { snapshot ->
                    val topikMap =
                        snapshot.documents.associateBy { it.id }  // id sama dengan ID topik utama

                    val gabungan = listData.map { topik ->
                        val userMeta = topikMap[topik.id]
                        topik.copy(
                            soal = userMeta?.getString("soal") ?: "0",
                            materi = userMeta?.getString("materi") ?: "0",
                            vidio = userMeta?.getString("vidio") ?: "0"
                        )
                    }

                    // Sekarang gabungan adalah data lengkap
                    listData.clear()
                    listData.addAll(gabungan)

                }
        }


    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 34.dp)
            .background(color = Color(0xffF5F9FF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        itemsIndexed(listData) { index, data ->
            val isActive = index == 0 // Topik pertama aktif, sisanya
            val icon = if (isActive) Icons.Default.PlayArrow else Icons.Default.Lock
            val iconTint = if (isActive) Color.White else Color.Black
            val iconBg = if (isActive) Color(0xFF337DFF) else Color(0xffCCCCCC)

            Card(
                modifier = Modifier
                    .padding(end = 34.dp)
                    .fillMaxWidth()
                    .height(190.dp)
                    .clickable(enabled = isActive) {
                        if (isActive) {
                            navController.navigate(AppScreen.Home.Semester.Topik.route)
                        }
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isActive) Color(0xFFC2D8FF) else Color(0xFFE0E0E0)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        "Topik ${data.nomor} - ${data.nama!!}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        fontFamily = poppinsfamily,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
                            .align(Alignment.TopStart)
                    )

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(start = 16.dp, bottom = 6.dp)
                    ) {
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

                        Text(
                            "Tahap",
                            fontSize = 12.sp,
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xff1F1F39)
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Materi ",
                                fontWeight = FontWeight.Bold,
                                color = if (data.materi == "1") Color(0xff0961F5) else Color.Black,
                                fontSize = 14.sp,
                                fontFamily = poppinsfamily
                            )
                            Text(
                                "Video",
                                color = if (data.materi == "1") Color(0xff0961F5) else Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = poppinsfamily
                            )
                            if (data.materi == "0") {
                                Text(
                                    " (Terkunci) ",
                                    color = Color.Black,
                                    fontSize = 10.sp,
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Text(
                                "Soal",
                                color = if (data.vidio == "1") Color(0xff0961F5) else Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = poppinsfamily
                            )

                            if (data.materi == "0") {
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

                    // Icon di pojok kanan atas
                    IconButton(
                        onClick = { if (isActive) { /* aksi */ } },
                        enabled = isActive,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(top = 12.dp, end = 24.dp)
                            .size(32.dp)
                            .background(iconBg,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (isActive) "Play" else "Lock",
                            tint = iconTint
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
        }


    }

}

fun gabungkanDenganUserData(topikList: List<TopikModel>) {
    val uid = FirebaseAuth.getInstance().uid
    val db = Firebase.firestore

    db.collection("users").document(uid.toString())
        .collection("topik")
        .get()
        .addOnSuccessListener { snapshot ->
            val topikMap =
                snapshot.documents.associateBy { it.id }  // id sama dengan ID topik utama

            val gabungan = topikList.map { topik ->
                val userMeta = topikMap[topik.id]
                topik.copy(
                    soal = userMeta?.getString("soal") ?: "0",
                    materi = userMeta?.getString("materi") ?: "0",
                    vidio = userMeta?.getString("vidio") ?: "0"
                )
            }

            // Sekarang gabungan adalah data lengkap

        }
}