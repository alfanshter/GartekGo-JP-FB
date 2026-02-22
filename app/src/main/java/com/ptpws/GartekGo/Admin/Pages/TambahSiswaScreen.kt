package com.ptpws.GartekGo.Admin.Pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.Dialog.TambahsiswaDialog
import com.ptpws.GartekGo.model.UsersModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TambahSiswaScreen(navController: NavController) {
    // Filter untuk tingkat kelas
    val tingkatOptions = listOf("Semua", "X", "XI", "XII")
    var selectedTingkat by remember { mutableStateOf("Semua") }

    // Filter untuk program keahlian
    val programKeahlianOptions = listOf("Semua", "TKP1", "TKP2", "GEO1", "GEO2", "DPIB1", "DPIB2")
    var selectedProgramKeahlian by remember { mutableStateOf("Semua") }

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val userList = remember { mutableStateListOf<UsersModel>() }
    var userModel by remember { mutableStateOf(UsersModel()) }

    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result.documents) {
                    val user = doc.toObject(UsersModel::class.java)?.copy(uid = doc.id)
                    if (user != null) userList.add(user)

                }
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "gagal ambil data: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    Scaffold(
        //topbar start
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Siswa",
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
        //topbar start
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Arahkan ke screen tambah soal, kirim idTopik atau semester jika perlu
                    showDialog = true
                    userModel = UsersModel()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Soal")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF5F9FF))
                .padding(innerPadding)
        ) {
            // Filter Tingkat
            Text(
                text = "Filter Tingkat:",
                fontFamily = poppinsfamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            FlowRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(color = Color(0xffF5F9FF)),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tingkatOptions.forEach { tingkat ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (selectedTingkat == tingkat) Color(0xFF2962FF) else Color.Gray
                        ),
                        color = if (selectedTingkat == tingkat) Color(0xFFE3F2FD) else Color.Transparent,
                        modifier = Modifier
                            .height(30.dp)
                            .clickable { selectedTingkat = tingkat }
                    ) {
                        Text(
                            text = tingkat,
                            fontWeight = if (selectedTingkat == tingkat) FontWeight.Bold else FontWeight.Normal,
                            fontFamily = poppinsfamily,
                            fontSize = 11.sp,
                            color = if (selectedTingkat == tingkat) Color(0xFF2962FF) else Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Filter Program Keahlian
            Text(
                text = "Filter Program Keahlian:",
                fontFamily = poppinsfamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )
            FlowRow(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .background(color = Color(0xffF5F9FF)),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                programKeahlianOptions.forEach { programKeahlian ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (selectedProgramKeahlian == programKeahlian) Color(0xFF2962FF) else Color.Gray
                        ),
                        color = if (selectedProgramKeahlian == programKeahlian) Color(0xFFE3F2FD) else Color.Transparent,
                        modifier = Modifier
                            .height(30.dp)
                            .clickable { selectedProgramKeahlian = programKeahlian }
                    ) {
                        Text(
                            text = programKeahlian,
                            fontWeight = if (selectedProgramKeahlian == programKeahlian) FontWeight.Bold else FontWeight.Normal,
                            fontFamily = poppinsfamily,
                            fontSize = 11.sp,
                            color = if (selectedProgramKeahlian == programKeahlian) Color(0xFF2962FF) else Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Filter dan sort data siswa
            val filteredUserList = userList
                .filter { user ->
                    // Filter tingkat berdasarkan field kelas (X, XI, XII)
                    val tingkatMatch = if (selectedTingkat == "Semua") {
                        true
                    } else {
                        user.kelas.trim().uppercase() == selectedTingkat.uppercase()
                    }

                    // Filter program keahlian berdasarkan field program_keahlian (TKP2, GEO1, dll)
                    val programMatch = if (selectedProgramKeahlian == "Semua") {
                        true
                    } else {
                        // Normalisasi program keahlian user (hapus spasi, uppercase)
                        val userProgram = user.program_keahlian.trim().replace(" ", "").uppercase()
                        val selectedProgramNormalized = selectedProgramKeahlian.trim().replace(" ", "").uppercase()

                        when {
                            // Exact match setelah normalisasi (TKP1 = TKP1, TKP2 = TKP2, GEO2 = GEO2, dll)
                            userProgram == selectedProgramNormalized -> true

                            // Jika user program tanpa angka, cocokkan dengan kategori 1
                            selectedProgramNormalized.endsWith("1") &&
                            userProgram == selectedProgramNormalized.dropLast(1) -> true

                            else -> false
                        }
                    }

                    tingkatMatch && programMatch
                }
                .sortedBy { it.nomor_absen }

            // LazyColumn content di bawahnya
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xffF5F9FF))
            ) {
                items(filteredUserList) { data ->
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .clickable {
                                showDialog = true
                                userModel = data
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(
                                    text = "Nama :",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = data.nama,
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                            }
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(
                                    text = "Email :",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = data.email,
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                            }
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(
                                    text = "No. Absen :",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = data.nomor_absen.toString(),
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                            }
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(
                                    text = "Kelas :",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = data.kelas,
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                            }

                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(
                                    text = "Program Keahlian :",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = data.program_keahlian,
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


    if (showDialog) {
        TambahsiswaDialog(
            onDismis = {
                showDialog = false

            },
            onSave = { user ->
                Log.d("dinda", "TambahSiswaScreen: $user")
                if (userModel.uid != null) {
                    Toast.makeText(context, "update siswa berhasil", Toast.LENGTH_SHORT).show()
                    val index = userList.indexOfFirst { it.uid == user.uid }
                    if (index != -1) {
                        userList[index] = user.copy(
                            nama = user.nama,
                            email = user.email,
                            nomor_absen = user.nomor_absen,
                            kelas = user.kelas,
                            program_keahlian = user.program_keahlian,
                        )
                    }
                } else {
                    Toast.makeText(context, "Registrasi siswa berhasil", Toast.LENGTH_SHORT).show()
                    userList.add(user) // <-- Tambah ke list utama

                }

            },
            usersModel = userModel,
            onDelete = { uid ->
                Toast.makeText(
                    context,
                    "User berhasil di hapus",
                    Toast.LENGTH_SHORT
                ).show()

                userList.removeIf { it.uid == uid }

            }
        )

    }


}


@Preview(showBackground = true)
@Composable
private fun PenilaianSiswaPreview() {
    TambahSiswaScreen(navController = rememberNavController())

}
