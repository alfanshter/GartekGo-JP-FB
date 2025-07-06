package com.ptpws.GartekGo.Semester

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.ptpws.GartekGo.Admin.model.NilaiModel
import com.ptpws.GartekGo.Admin.model.SoalModel
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.tasks.await


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoalScreen(navController: NavController, idtopik: String) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var selectedOption by remember { mutableStateOf("") }
    var soalList by remember { mutableStateOf<List<SoalModel>>(emptyList()) }
    var currentIndex by remember { mutableStateOf(0) }
    var jawabansiswa by remember { mutableStateOf(mutableListOf<String>()) }
    var tampilkannilai by remember { mutableStateOf(false) }
    var nilai by remember { mutableStateOf(0) }
    var modeReview by remember { mutableStateOf(false) }
    var sudahLulus by remember { mutableStateOf(false) }

    val db = FirebaseFirestore.getInstance()
    val topikRef = db.collection("topik").document(idtopik)

    suspend fun getSoalByTopikId(idTopik: String): List<SoalModel> {
        val soalList = mutableListOf<SoalModel>()
        val topikRef: DocumentReference = db.collection("topik").document(idTopik)

        val snapshot = db.collection("soal")
            .whereEqualTo("topik", topikRef)
            .get()
            .await()

        for (doc in snapshot.documents) {
            val soal = doc.toObject(SoalModel::class.java)
            if (soal != null) {
                soalList.add(soal)
            }
        }
        return soalList
    }

    LaunchedEffect(idtopik) {
        soalList = getSoalByTopikId(idtopik)

        val userUid = auth.currentUser?.uid ?: ""
        val snapshot = db.collection("nilai")
            .whereEqualTo("uid", userUid)
            .whereEqualTo("topik", topikRef)
            .get()
            .await()

        if (!snapshot.isEmpty) {
            val nilaiDoc = snapshot.documents.first()
            val statusLulus = nilaiDoc.getString("status_lulus")
            if (statusLulus == "LULUS") {
                sudahLulus = true
                modeReview = true
                currentIndex = 0

                //Tambahkan ini supaya nilai tidak 0
                nilai = (nilaiDoc.getLong("nilai") ?: 0L).toInt()

                val jawabanSiswaDb = nilaiDoc["jawaban_siswa"] as? List<String>
                if (jawabanSiswaDb != null) {
                    jawabansiswa.clear()
                    jawabansiswa.addAll(jawabanSiswaDb)
                    selectedOption = jawabanSiswaDb.getOrNull(0) ?: ""
                }
            }
        }
    }

    if (soalList.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (tampilkannilai) {
        if (nilai >= 65) {
            CardLulus(
                nilai = nilai,
                onReview = {
                    modeReview = true
                    tampilkannilai = false
                    currentIndex = 0
                    selectedOption = jawabansiswa.getOrNull(0) ?: ""
                },
                onLanjut = {
                    navController.navigate("${AppScreen.Home.Semester.Topik.Upload.route}/$idtopik")
                },
            )
        } else {
            CardTidakLulus(
                nilai = nilai,
                onReview = {
                    modeReview = true
                    tampilkannilai = false
                    currentIndex = 0
                    selectedOption = jawabansiswa.getOrNull(0) ?: ""
                },
                onCobaLagi = {
                    jawabansiswa.clear()
                    selectedOption = ""
                    currentIndex = 0
                    tampilkannilai = false
                    nilai = 0
                    modeReview = false
                }
            )
        }
        return
    }

    val soal = soalList.getOrNull(currentIndex)
    val optionsMap = soal?.jawaban?.entries?.toList() ?: emptyList()
    val jawabanBenar = soal?.jawaban_benar
    val jawabanSiswa = jawabansiswa.getOrNull(currentIndex)

    Scaffold(
        containerColor = Color(0xffF5F9FF),
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = if (modeReview || sudahLulus) "Topik 1 : REVIEW" else "Topik 1 : SOAL",
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
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xffF5F9FF))
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (content, button) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(button.top)
                        height = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 24.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${currentIndex + 1}/${soalList.size}",
                        fontSize = 16.sp,
                        fontFamily = jostfamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                item {
                    Text(
                        text = soal?.soal ?: "",
                        fontSize = 16.sp,
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                items(optionsMap.size) { index ->
                    val option = optionsMap[index]
                    val optionKey = option.key
                    val optionValue = option.value

                    val isSelected = if (modeReview || sudahLulus) optionKey == jawabanSiswa else optionKey == selectedOption
                    val isBenar = optionKey == jawabanBenar
                    val isSalahDipilih = optionKey == jawabanSiswa && optionKey != jawabanBenar

                    val radioColor = when {
                        !modeReview && !sudahLulus -> Color(0xFF007AFF)
                        isBenar -> Color(0xFF4CAF50)
                        isSalahDipilih -> Color(0xFFF44336)
                        else -> Color.Gray
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                if (!modeReview && !sudahLulus) {
                                    selectedOption = optionKey
                                }
                            },
                            enabled = !modeReview && !sudahLulus,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = radioColor,
                                unselectedColor = if ((modeReview || sudahLulus) && isSelected) radioColor else Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "${optionKey.uppercase()}.",
                            fontFamily = poppinsfamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = optionValue,
                            fontFamily = poppinsfamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isSalahDipilih) Color.Red else Color.Black
                        )

                        if (modeReview || sudahLulus) {
                            Spacer(modifier = Modifier.width(8.dp))
                            when {
                                isSalahDipilih -> Text("(Salah)", color = Color(0xFFF44336), fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsfamily)
                                isBenar -> Text("(Benar)", color = Color(0xFF4CAF50), fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = poppinsfamily)
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            Button(
                onClick = {
                    if (modeReview || sudahLulus) {
                        if (currentIndex < soalList.size - 1) {
                            currentIndex++
                        } else {
                            tampilkannilai = true
                        }
                    } else {
                        if (selectedOption.isEmpty()) {
                            Toast.makeText(context, "Masukkan dulu jawaban Anda", Toast.LENGTH_SHORT).show()
                        } else {
                            jawabansiswa.add(selectedOption)

                            if (currentIndex < soalList.size - 1) {
                                currentIndex++
                                selectedOption = ""
                            } else {
                                val benar = soalList.indices.count { i ->
                                    soalList[i].jawaban_benar == jawabansiswa.getOrNull(i)
                                }
                                val score = ((benar.toDouble() / soalList.size) * 100).toInt()
                                nilai = score

                                val nilaiModel = NilaiModel(
                                    uid = auth.currentUser?.uid ?: "unknown",
                                    semester = "semester1",
                                    topik = topikRef,
                                    nilai = score,
                                    jawaban_siswa = jawabansiswa,
                                    jawaban_benar = soalList.map { it.jawaban_benar },
                                    benar_siswa = benar,
                                    total_soal = soalList.size,
                                    status_lulus = if (score >= 65) "LULUS" else "TIDAK LULUS",
                                    timestamp = Timestamp.now()
                                )

                                //update nilai difirestore

                                val uid = auth.currentUser?.uid ?: ""
                                val userTopikRef = db.collection("users").document(uid).collection("topik").document(idtopik)

                                db.collection("nilai")
                                    .add(nilaiModel)
                                    .addOnSuccessListener {
                                        if (score >= 65) {
                                            userTopikRef.update("soal", "1")
                                                .addOnSuccessListener {
                                                    tampilkannilai = true
                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(context, "Gagal update progres topik", Toast.LENGTH_SHORT).show()
                                                    tampilkannilai = true
                                                }
                                        } else {
                                            tampilkannilai = true
                                        }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Gagal menyimpan nilai", Toast.LENGTH_SHORT).show()
                                    }

                            }
                        }
                    }
                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009A0F)),
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 24.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = if (currentIndex == soalList.size - 1 && !modeReview && !sudahLulus) "SELESAI" else "SELANJUTNYA",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsfamily,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next",
                                tint = Color(0xFF009A0F),
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
private fun SoalScreenPreview() {
    SoalScreen(navController = rememberNavController(), idtopik = "")


}