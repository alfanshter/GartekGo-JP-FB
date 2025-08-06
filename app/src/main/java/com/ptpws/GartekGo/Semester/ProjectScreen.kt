package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.Dialog.TambahProjectSiswaDialog
import com.ptpws.GartekGo.model.ProjectUploadData
import com.ptpws.GartekGo.model.TopikModel

@Composable
fun ProjectScreen(navController: NavController, semester: Int) {
    val db = Firebase.firestore
    val listData = remember { mutableStateListOf<TopikModel>() }
    val userUid = FirebaseAuth.getInstance().uid

    // Map: idTopik -> Pair(status, nilai)
    val projectUploadMap = mutableMapOf<String, ProjectUploadData>()
    var isAddProject by remember { mutableStateOf(false) }
    var topikModel by remember { mutableStateOf(TopikModel()) }
    var gambarSoalProject by remember { mutableStateOf("") }
    var idProject by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var program_keahlian by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        val getdata = db.collection("topik")
            .whereEqualTo("semester", semester)
            .orderBy("nomor").get()
        getdata.addOnSuccessListener { data ->
            listData.clear()
            for (datas in data.documents) {
                val topik = datas.toObject(TopikModel::class.java)
                if (topik != null) {
                    listData.add(topik.copy(id = datas.id))
                }
            }
        }

        // Ambil data user
        db.collection("users").document(userUid.toString()).get()
            .addOnSuccessListener { userDoc ->
                val dataKelas = userDoc.getString("kelas") ?: ""
                val dataProgramKeahlian =
                    userDoc.getString("program_keahlian") ?: ""

                kelas = dataKelas
                program_keahlian = dataProgramKeahlian
            }


        db.collection("project_uploads")
            .whereEqualTo("uid", userUid)
            .get().addOnSuccessListener { snapshot ->
                for (projDoc in snapshot.documents) {
                    val idTopik = projDoc.getString("id_topik")
                    val idproject = projDoc.getString("id_project")
                    val nilai = projDoc.getLong("nilai")?.toInt()
                    val imageUrl = projDoc.getString("imageUrl").toString()
                    val status = projDoc.getBoolean("status")

                    if (idTopik != null) {
                        projectUploadMap[idTopik] =
                            ProjectUploadData(status, nilai, imageUrl, idTopik, idproject)
                    }
                }
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
            .padding(top = 8.dp)
    ) {
        itemsIndexed(listData) { _, data ->

            val projectData = projectUploadMap[data.id]
            val statusProject = projectData?.status
            val nilaiProject = projectData?.nilai
            val gambarProject = projectData?.imageUrl
            val dataIdProject = projectData?.id_project
            if (dataIdProject != null) {
                idProject = dataIdProject
            }


            val initialProjectData = remember { projectUploadMap[data.id] }

            // Gunakan warna biru jika data ada, abu-abu jika tidak
            val cardColor = if (initialProjectData != null) {
                Color(0xFFC2D8FF) // biru
            } else {
                Color(0xFFE0E0E0) // abu
            }

            // Pilih ikon berdasarkan statusProject (atau idTopik, sesuai maksud kamu)
            val iconVector = when {
                initialProjectData != null -> Icons.Default.PlayArrow
                else -> Icons.Default.Refresh
            }

            val iconBgColor = if (initialProjectData != null) {
                Color(0xFF3366FF) // biru
            } else {
                Color(0xFF00AA00) // hijau
            }

            val iconTint = Color.White // selalu putih seperti di kode kamu

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 34.dp, end = 34.dp)
                    .clickable {
                        topikModel = data
                        gambarSoalProject = gambarProject.toString()
                        isAddProject = true
                    },
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                text = "Topik ${data.nomor} - ${data.nama ?: ""}",
                                fontFamily = poppinsfamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(
                                        end = 12.dp,
                                    )
                                    .weight(1f)

                            )

                            IconButton(
                                onClick = { },
                                enabled = statusProject == true,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(color = iconBgColor, shape = CircleShape)
                            ) {
                                Icon(
                                    imageVector = iconVector,
                                    contentDescription = "Icon",
                                    tint = iconTint
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (statusProject == true) "Sudah dinilai" else "Belum dinilai",
                            fontFamily = poppinsfamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = if (nilaiProject != null) "Nilai : $nilaiProject" else "Nilai : -",
                            fontFamily = poppinsfamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }


                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }


    if (isAddProject) {
        TambahProjectSiswaDialog(onDismiss = {
            isAddProject = false
        }, topikModel = topikModel, onSave = {

        }, gambarSoalProject, idProject,kelas,program_keahlian)

    }
}


@Preview(showBackground = true)
@Composable
private fun ProjectScreenPreview() {
    ProjectScreen(navController = rememberNavController(), semester = 0)

}

