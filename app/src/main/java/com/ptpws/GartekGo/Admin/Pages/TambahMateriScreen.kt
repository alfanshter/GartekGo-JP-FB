package com.ptpws.GartekGo.Admin.Pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import com.ptpws.GartekGo.Admin.Dialog.TambahMateriDialog
import com.ptpws.GartekGo.model.TopikModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahMateriScreen(
    navController: NavController,
    outerPadding: PaddingValues = PaddingValues()
) {
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
                        windowInsets = WindowInsets(0),
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
            }, contentWindowInsets = WindowInsets(0),
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
                    val semester = if (page == 0) "1" else "2" // ← sesuai tab

                    MateriListContent(
                        onTambahClick = {

                        },
                        semester = if (page == 0) "1" else "2"

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
    onTambahClick: () -> Unit,
    semester: String
) {
    var showDialogmateri by remember { mutableStateOf(false) }
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
                    val topik = doc.toObject(TopikModel::class.java)?.copy(id = doc.id)
                    if (topik != null) topikList.add(topik)

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

                        showDialogmateri = true
                        topikModel = data
                        idTopik = data.id
                        isUpdate = true
                        idLama = data.id
                        Log.d("dinda", idLama)

                    },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFDDECFF)),
                shape = RoundedCornerShape(23.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Bagian atas: nama topik
                    Column(
                        modifier = Modifier
                            .padding(start = 22.dp, top = 12.dp)
                    ) {
                        Text(
                            text = "Topik ${data.nomor} - ${data.nama!!}",
                            fontWeight = FontWeight.Bold,
                            fontFamily = poppinsfamily,
                            fontSize = 12.sp,
                            color = Color.Black,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Row(modifier = Modifier.fillMaxWidth().align(Alignment.BottomStart).padding(end = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        // Bagian bawah kiri: tanggal upload
                        Text(
                            text = "${tanggalUpload}",
                            fontSize = 12.sp,
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 22.dp, bottom = 12.dp)
                        )

                        Text(
                            text = if (data.file_materi!=null) "Materi ada" else "Materi tidak ada",
                            fontSize = 12.sp,
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 22.dp, bottom = 12.dp)
                        )
                    }

                }
            }

        }

    }


    if (showDialogmateri == true) {
        TambahMateriDialog(
            onDismis = {
                showDialogmateri = false

            }, semester = semester,
            onSave = { topik ->
                Log.d("dinda", topik.toString())

                if (idTopik.isNotBlank()) {
                    val index = topikList.indexOfFirst { it.id == topik.id }
                    if (index != -1) {
                        val topikLama = topikList[index]
                        // Hanya update file_materi dan nama_file, field lain tetap
                        topikList[index] = topikLama.copy(
                            file_materi = topik.file_materi,
                            nama_file = topik.nama_file
                        )
                    }
                }
            },
            topikModel = topikModel,

        )
    }

}
