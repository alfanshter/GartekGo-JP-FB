package com.ptpws.GartekGo.Admin.Pages

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.model.NilaiModel
import com.ptpws.GartekGo.model.UsersModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.tasks.await

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NilaiSiswa(
    navController: NavController,
    outerPadding: PaddingValues = PaddingValues(),
    usersModel: UsersModel
) {

    val chipLabelsnilaisiswa =
        listOf("Semester 1", "Semester 2")
    var selectedChipnilaisiswa by remember { mutableStateOf(1) } // index chip yang aktif

    var isLoading by remember { mutableStateOf(false) }

    val userList = remember { mutableStateListOf<NilaiModel>() }
    var lastVisible by remember { mutableStateOf<DocumentSnapshot?>(null) }
    var endReached by remember { mutableStateOf(false) }

    // State untuk rata-rata nilai
    var rataSoalSem1 by remember { mutableStateOf(0.0) }
    var rataSoalSem2 by remember { mutableStateOf(0.0) }
    var rataProjectSem1 by remember { mutableStateOf(0.0) }
    var rataProjectSem2 by remember { mutableStateOf(0.0) }
    var rataKeseluruhanSem1 by remember { mutableStateOf(0.0) }
    var rataKeseluruhanSem2 by remember { mutableStateOf(0.0) }


    LaunchedEffect(Unit) {
        fetchUsers(
            usersModel = usersModel,
            userList = userList,
            limit = 10,
            lastVisible = null,
            onLastVisibleChanged = { lastVisible = it },
            onLoadingChanged = { isLoading = it },
            onEndReached = { endReached = it }
        )
    }

    // Fetch rata-rata nilai soal dan project
    LaunchedEffect(usersModel.uid) {
        val db = Firebase.firestore

        // Hitung rata-rata nilai soal per semester
        db.collection("nilai")
            .whereEqualTo("uid", usersModel.uid)
            .get()
            .await()
            .let { snapshot ->
                val nilaiSem1 = snapshot.documents
                    .mapNotNull { it.toObject(NilaiModel::class.java) }
                    .filter { it.semester == 1 }
                    .mapNotNull { it.nilai?.toDouble() }

                val nilaiSem2 = snapshot.documents
                    .mapNotNull { it.toObject(NilaiModel::class.java) }
                    .filter { it.semester == 2 }
                    .mapNotNull { it.nilai?.toDouble() }

                rataSoalSem1 = if (nilaiSem1.isNotEmpty()) nilaiSem1.average() else 0.0
                rataSoalSem2 = if (nilaiSem2.isNotEmpty()) nilaiSem2.average() else 0.0
            }

        // Hitung rata-rata nilai project per semester
        db.collection("project_uploads")
            .whereEqualTo("uid", usersModel.uid)
            .get()
            .await()
            .let { snapshot ->
                val projectSem1 = snapshot.documents
                    .filter { it.getLong("semester") == 1L }
                    .mapNotNull { it.getLong("nilai")?.toDouble() }

                val projectSem2 = snapshot.documents
                    .filter { it.getLong("semester") == 2L }
                    .mapNotNull { it.getLong("nilai")?.toDouble() }

                rataProjectSem1 = if (projectSem1.isNotEmpty()) projectSem1.average() else 0.0
                rataProjectSem2 = if (projectSem2.isNotEmpty()) projectSem2.average() else 0.0
            }

        // Hitung rata-rata keseluruhan (soal + project)
        rataKeseluruhanSem1 = if (rataSoalSem1 > 0 || rataProjectSem1 > 0) {
            (rataSoalSem1 + rataProjectSem1) / 2
        } else 0.0

        rataKeseluruhanSem2 = if (rataSoalSem2 > 0 || rataProjectSem2 > 0) {
            (rataSoalSem2 + rataProjectSem2) / 2
        } else 0.0
    }


    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Nilai",
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
        val combinedPadding = PaddingValues(
            top = innerPadding.calculateTopPadding(),
            bottom = outerPadding.calculateBottomPadding()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF5F9FF))
                .padding(combinedPadding)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Semester 1
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Rata Nilai Semester 1",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Soal          : ${String.format("%.0f", rataSoalSem1)}",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                    Text(
                        "Project       : ${String.format("%.0f", rataProjectSem1)}",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                    Text(
                        "Keseluruhan   : ${String.format("%.0f", rataKeseluruhanSem1)}",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))


                // Spacer untuk garis atau jarak (opsional)
                Spacer(modifier = Modifier.width(16.dp))

                // Semester 2
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Rata Nilai Semester 2",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Soal          : ${String.format("%.0f", rataSoalSem2)}",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                    Text(
                        "Project       : ${String.format("%.0f", rataProjectSem2)}",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                    Text(
                        "Keseluruhan   : ${String.format("%.0f", rataKeseluruhanSem2)}",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black, fontSize = 14.sp
                    )
                }
            }
            FlowRow(
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = Color(0xffF5F9FF)),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chipLabelsnilaisiswa.forEachIndexed { index, label ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (selectedChipnilaisiswa == index) Color(0xFF2962FF) else Color.Gray
                        ),
                        color = Color.Transparent,
                        modifier = Modifier
                            .height(30.dp)
                            .clickable { selectedChipnilaisiswa = index }
                    ) {
                        Text(
                            text = label,
                            fontWeight = if (selectedChipnilaisiswa == index) FontWeight.Bold else FontWeight.Normal,
                            fontFamily = poppinsfamily,
                            fontSize = 11.sp,
                            color = if (selectedChipnilaisiswa == index) Color(0xFF2962FF) else Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Filter data berdasarkan semester yang dipilih
            val filteredUserList = userList.filter {
                it.semester == (selectedChipnilaisiswa + 1) // index 0 = semester 1, index 1 = semester 2
            }

            TopikCard(navController, filteredUserList, usersModel.uid ?: "")

        }
    }

}


@Preview(showBackground = true)
@Composable
private fun NilaiSiwaPreview() {
    val dummyUser = UsersModel(
        email = "dummy@example.com",
        nama = "Budi Santoso",
        kelas = "XII",
        nomor_absen = 12,
        uid = "uid_dummy",
        program_keahlian = "TKP",
        role = "user",
        createdAt = Timestamp.now()
    )

    NilaiSiswa(
        navController = rememberNavController(),
        outerPadding = PaddingValues(16.dp),
        usersModel = dummyUser
    )
}



@Composable
fun TopikCard(navController: NavController, nilaiList: List<NilaiModel>, uid: String) {
    val db = Firebase.firestore
    val projectMap = remember { mutableStateOf<Map<String, Long>>(emptyMap()) }

    // Fetch data project berdasarkan uid
    LaunchedEffect(uid) {
        db.collection("project_uploads")
            .whereEqualTo("uid", uid)
            .get()
            .await()
            .let { snapshot ->
                val map = mutableMapOf<String, Long>()
                snapshot.documents.forEach { doc ->
                    val idTopik = doc.getString("id_topik")
                    val nilai = doc.getLong("nilai")
                    if (idTopik != null && nilai != null) {
                        map[idTopik] = nilai
                    }
                }
                projectMap.value = map
            }
    }

    LazyColumn {
        items(nilaiList) { data ->
            var namaTopik by remember { mutableStateOf("") }
            var nomorTopik by remember { mutableStateOf(0) }

            // Fetch nama topik dari DocumentReference
            LaunchedEffect(data.topik) {
                data.topik?.get()?.await()?.let { doc ->
                    namaTopik = doc.getString("nama") ?: ""
                    nomorTopik = doc.getLong("nomor")?.toInt() ?: 0
                }
            }

            // Ambil nilai project untuk topik ini
            val nilaiProject = projectMap.value[data.topik?.id] ?: 0L

            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Topik dengan format: "Topik 1 : Nama topik..."
                    Text(
                        text = if (namaTopik.isNotEmpty()) "Topik $nomorTopik : $namaTopik" else "Loading...",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            text = "Nilai Soal :",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${data.nilai ?: 0}",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            text = "Nilai Project :",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$nilaiProject",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Button Lihat Gambar
                    if (nilaiProject > 0) {
                        androidx.compose.material3.Button(
                            onClick = {
                                // Navigate ke halaman lihat gambar project
                                // Bisa pass id_topik atau data yang dibutuhkan
                            },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF0961F5)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Lihat Gambar Project",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}


suspend fun fetchUsers(
    usersModel: UsersModel,
    userList: SnapshotStateList<NilaiModel>,
    limit: Long,
    lastVisible: DocumentSnapshot?,
    onLastVisibleChanged: (DocumentSnapshot?) -> Unit,
    onLoadingChanged: (Boolean) -> Unit,
    onEndReached: (Boolean) -> Unit,
) {

    val db = Firebase.firestore

    onLoadingChanged(true)
    try {
        var query = db.collection("nilai")
            .whereEqualTo("uid", usersModel.uid)
            .limit(limit)

        if (lastVisible != null) {
            query = query.startAfter(lastVisible)
        }

        val snapshot = query.get().await()
        println("📦 Data fetched: ${snapshot.documents.size}")

        if (!snapshot.isEmpty) {
            val newUsers = snapshot.documents.mapNotNull { doc ->
                doc.toObject(NilaiModel::class.java)?.copy(uid = doc.id)
            }
            userList.addAll(newUsers)
            println("📈 userList.size = ${userList.size}")
            onLastVisibleChanged(snapshot.documents.last())
        } else {
            onEndReached(true)
        }
    } catch (e: Exception) {
        onEndReached(true)
        e.printStackTrace()
    }
    onLoadingChanged(false)
}


