package com.ptpws.GartekGo.Admin.Pages

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.model.TopikModel
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahSoalScreen(navController: NavController, outerPadding: PaddingValues = PaddingValues()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Semester 1", "Semester 2")
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
            val combinedPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = outerPadding.calculateBottomPadding()
            )
            Column(
                modifier = Modifier
                    .padding(combinedPadding)
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
                    ListSoalContent(
                        onTambahClick = {

                        },
                        semester = if (page == 0) "1" else "2",
                        navController

                    )


                }

                // LazyColumn berisi semua konten termasuk tombol
            }
        }
    }


}

@Preview
@Composable
private fun TambahSoalScreenPreview() {
    TambahSoalScreen(navController = rememberNavController())

}

@Composable
fun ListSoalContent(onTambahClick: () -> Unit, semester: String, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    val topikList = remember { mutableStateListOf<TopikModel>() }
    val context = LocalContext.current
    var idTopik by remember { mutableStateOf("") }
    var topikModel by remember { mutableStateOf(TopikModel()) }
    var isUpdate by remember { mutableStateOf(false) }
    var idLama by remember { mutableStateOf("") }
    LaunchedEffect(semester) {
        val db = Firebase.firestore
        val semesterLabel = if (semester == "1") "Semester 1" else "Semester 2"
        db.collection("topik")
            .whereEqualTo("semester", semesterLabel)
            .orderBy("nomor")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result.documents) {
                    val topikRef = doc.reference
                    val topikData = doc.toObject(TopikModel::class.java)?.copy(id = doc.id)

                    if (topikData != null) {
                        // Hitung jumlah soal yang berelasi dengan topik ini
                        db.collection("soal")
                            .whereEqualTo(
                                "topik",
                                topikRef
                            )  // asumsi field 'topik' di soal adalah DocumentReference
                            .get()
                            .addOnSuccessListener { soalSnapshot ->
                                val jumlah = soalSnapshot.size()
                                val updatedTopik = topikData.copy(jumlahSoal = jumlah)
                                topikList.add(updatedTopik)
                            }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "gagal ambil data: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 34.dp, end = 34.dp)
            .background(color = Color(0xffF5F9FF)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp)
    ) {
        items(topikList) { data ->
            val tanggalUpload = data.uploadedMateriAt?.toDate()?.let {
                SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(it)
            } ?: "-"

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(vertical = 6.dp)
                    .clickable {
                        navController.navigate(AppScreen.Home.Admin.DataSoal.createRoute(data))

                    },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDDECFF)),
                shape = RoundedCornerShape(23.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 22.dp, end = 21.dp, top = 12.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "Topik ${data.nomor} - ${data.nama!!}", fontWeight = FontWeight.Bold,
                        fontFamily = poppinsfamily,
                        fontSize = 12.sp,
                        color = Color.Black,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis

                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    // Tambahkan spacer dengan weight untuk dorong Row ke bawah
                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "${tanggalUpload}",
                            fontSize = 12.sp,
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Text(
                            text = "${data.jumlahSoal} Soal ",
                            fontSize = 12.sp,
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )


                        Text(
                            text = if (data.status_soal == 0) "-" else if (data.status_soal == 1) "Published" else "Draft",
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsfamily,
                            fontSize = 12.sp,
                            color = Color.Black
                        )

                    }

                }
            }

            Spacer(Modifier.height(11.dp))


        }


    }


}