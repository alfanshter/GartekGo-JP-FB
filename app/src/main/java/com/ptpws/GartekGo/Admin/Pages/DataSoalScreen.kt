package com.ptpws.GartekGo.Admin.Pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.Dialog.TambahSoalDialog
import com.ptpws.GartekGo.Admin.model.SoalModel
import com.ptpws.GartekGo.Admin.model.TopikModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataSoalScreen(
    navController: NavController,
    outerPadding: PaddingValues = PaddingValues(),
    topikModel: TopikModel
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var soalModel by remember { mutableStateOf(SoalModel()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        Scaffold(
            //topbar start
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        // Arahkan ke screen tambah soal, kirim idTopik atau semester jika perlu
                        showDialog = true
                        soalModel = SoalModel()
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Soal")
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        windowInsets = WindowInsets(0),
                        title = {
                            Text(
                                text = "TOPIK ${topikModel.nomor}",
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

                // LazyColumn berisi semua konten termasuk tombol
                DataSoalContent(
                    topikModel,
                    showDialog,
                    onShowDialogChange = { showDialog = it },
                    soalModelChange = { soalModel = it },
                    soalModel = soalModel
                )
            }
        }
    }


}

@Preview
@Composable
private fun DataSoalScreenPreview() {
    DataSoalScreen(navController = rememberNavController(), topikModel = TopikModel())

}

@Composable
fun DataSoalContent(
    topikModel: TopikModel,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    soalModelChange: (SoalModel) -> Unit,
    soalModel: SoalModel?
) {

    val soalList = remember { mutableStateListOf<SoalModel>() }

    val context = LocalContext.current
    var isSaving by remember { mutableStateOf(false) }
    var idSoal by remember { mutableStateOf("") }

// 1. Simpan status aktif sebagai state Compose
    var isActive by remember { mutableStateOf(topikModel.status_soal == 1) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(topikModel.id) {
        val db = Firebase.firestore
        val reftopik = db.collection("topik").document(topikModel.id)

        db.collection("soal")
            .whereEqualTo("topik", reftopik)
            .get()
            .addOnSuccessListener { result ->
                soalList.clear() // optional, untuk mencegah duplikasi
                for (doc in result.documents) {
                    val soalData = doc.toObject(SoalModel::class.java)?.copy(id_soal = doc.id)
                    if (soalData != null) {
                        soalList.add(soalData)
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
        item {
            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = isActive,
                    onCheckedChange = { newValue ->
                        isLoading = true // Tampilkan loading

                        val firestore = FirebaseFirestore.getInstance()

                        val data = hashMapOf(
                            "status_soal" to if (newValue) 1 else 2
                        )

                        firestore.collection("topik").document(topikModel.id).update(data as Map<String, Any>)
                            .addOnSuccessListener {
                                isActive = newValue // <- ini yang mengubah tampilan Switch
                                isLoading = false
                            }
                            .addOnFailureListener {
                                isLoading = false
                                Toast.makeText(
                                    context,
                                    "gagal update data: ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                    }
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = if (isActive) "Published" else "Draft",
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsfamily,
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis

                )
            }

        }
        itemsIndexed(soalList) { index, data ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        soalModelChange(data)
                        onShowDialogChange(true)
                        idSoal = data.id_soal
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
                        text = "${index + 1}. ${data.soal}",
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsfamily,
                        fontSize = 12.sp,
                        color = Color.Black,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis

                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    data.jawaban.forEach { (nomor, jawaban) ->
                        Text(
                            text = "$nomor. $jawaban",
                            fontSize = 12.sp,
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Jawaban benar : ${data.jawaban_benar}",
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsfamily,
                        fontSize = 12.sp,
                        color = Color.Black,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis

                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Tambahkan spacer dengan weight untuk dorong Row ke bawah
                    Spacer(modifier = Modifier.weight(1f))


                }
            }

            Spacer(Modifier.height(11.dp))


        }


    }


    if (isLoading) {
        AlertDialog(
            onDismissRequest = { /* tidak bisa dismiss manual */ },
            title = null,
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Menyimpan perubahan...")
                }
            },
            confirmButton = {}
        )
    }

    if (showDialog) {
        TambahSoalDialog(onDismis = {
            onShowDialogChange(false)
        }, onSave = { soal ->
            Log.d("dinda", "DataSoalContent: $soalModel ")
            if (soalModel!!.topik != null) {
                val index = soalList.indexOfFirst { it.id_soal == soal.id_soal }
                if (index != -1) {
                    soalList[index] = soal.copy(
                        soal = soal.soal,
                        jawaban = soal.jawaban,
                        jawaban_benar = soal.jawaban_benar
                    )
                }
            } else {
                soalList.add(soal) // <-- Tambah ke list utama

            }
        }, topikModel, soalModel = soalModel,
            onDelete = { idSoal ->
            soalList.removeIf { it.id_soal == idSoal }
            Toast.makeText(
                context,
                "Soal disimpan",
                Toast.LENGTH_SHORT
            ).show()

        })

    }

    if (isSaving) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Menyimpan Soal...") },
            text = {
                Column {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(8.dp))
                    Text("Silakan tunggu...")
                }
            },
            confirmButton = {}
        )
    }

}