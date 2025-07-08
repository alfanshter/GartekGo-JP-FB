package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ptpws.GartekGo.Admin.Pages.NilaiSiswaComponent
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TampilanNilaiSiswa(
    navController: NavController,
    outerPadding: PaddingValues = PaddingValues()
) {
    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    var nilaiSoal by remember { mutableStateOf(0) }
    var dataLoaded by remember { mutableStateOf(false) }

    val chipLabelsnilaisiswa =
        listOf("Chip Filter", "Chip Filter", "Chip Filter", "Chip Filter", "Chip Filter")
    var selectedChipnilaisiswa by remember { mutableStateOf(1) }

    // ✅ HANYA ambil nilai berdasarkan UID tanpa filter semester
    LaunchedEffect(Unit) {
        firestore.collection("nilai")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
                var total = 0
                var count = 0

                for (document in result) {
                    val nilai = document.getLong("nilai")?.toInt() ?: 0
                    total += nilai
                    count++
                }

                nilaiSoal = if (count > 0) total / count else 0
                dataLoaded = true
            }
            .addOnFailureListener {
                dataLoaded = true
            }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
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
                    containerColor = Color(
                        0xffF5F9FF
                    )
                )
            )
        }, contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
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
            if (!dataLoaded) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Rata Nilai Semester 1",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Soal       : $nilaiSoal",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "Project    : 90",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "Keseluruhan   : 90",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Rata Nilai Semester 2",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Soal       : 90",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "Project    : 90",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "Keseluruhan  : 90",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black,
                            fontSize = 14.sp
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

                TampilanNilaiSiswaComponent()
            }
        }
    }
}


@Composable
fun TampilanNilaiSiswaComponent() {
    val db = FirebaseFirestore.getInstance()
    val uid = FirebaseAuth.getInstance().uid ?: ""

    var listData by remember { mutableStateOf<List<Pair<String, Int>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            // 1️⃣ Ambil semua nilai untuk user ini
            val nilaiSnapshot = db.collection("nilai")
                .whereEqualTo("uid", uid)
                .get()
                .await()

            val combined = mutableListOf<Pair<String, Int>>()

            // 2️⃣ Iterasi nilai
            for (doc in nilaiSnapshot.documents) {
                val topikRef = doc.getDocumentReference("topik")
                val nilaiSoal = doc.getLong("nilai")?.toInt() ?: 0

                if (topikRef != null) {
                    // 3️⃣ Ambil data topik
                    val topikSnapshot = topikRef.get().await()
                    val namaTopik = topikSnapshot.getString("nama") ?: "Topik"

                    combined.add(Pair(namaTopik, nilaiSoal))
                }
            }

            listData = combined
            isLoading = false
        } catch (e: Exception) {
            e.printStackTrace()
            isLoading = false
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listData) { item ->
                val (namaTopik, nilaiSoal) = item

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Topik:",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = namaTopik,
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Soal:",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = nilaiSoal.toString(),
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp, color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(vertical = 2.dp)) {
                            Text(
                                text = "Project:",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp, color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "-",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp, color = Color.Black
                            )
                        }
                    }
                }
                Spacer(Modifier.height(7.dp))
            }

        }
    }
}
