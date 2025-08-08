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
import androidx.compose.runtime.mutableStateMapOf
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
    val projectUploadMap = remember { mutableStateMapOf<String, ProjectUploadData>() }

    var isAddProject by remember { mutableStateOf(false) }
    var topikModel by remember { mutableStateOf(TopikModel()) }
    var gambarSoalProject by remember { mutableStateOf("") }
//    var idProject by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var program_keahlian by remember { mutableStateOf("") }
    var statusProject by remember { mutableStateOf(false) }
    var nilaiProject by remember { mutableStateOf(0) }
    var dataIdProject by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        // 1. Ambil data user
        db.collection("users").document(userUid.toString()).get()
            .addOnSuccessListener { userDoc ->
                kelas = userDoc.getString("kelas") ?: ""
                program_keahlian = userDoc.getString("program_keahlian") ?: ""
            }

        // 2. Ambil semua topik
        db.collection("topik")
            .whereEqualTo("semester", semester)
            .orderBy("nomor")
            .get()
            .addOnSuccessListener { topikSnapshot ->
                val tempTopikList = mutableListOf<TopikModel>()

                for (doc in topikSnapshot.documents) {
                    val topik = doc.toObject(TopikModel::class.java)?.copy(id = doc.id)
                    if (topik != null) {
                        tempTopikList.add(topik)
                    }
                }

                // 3. Ambil project uploads user
                db.collection("project_uploads")
                    .whereEqualTo("uid", userUid)
                    .get()
                    .addOnSuccessListener { projectSnapshot ->
                        val projectMap = mutableMapOf<String, ProjectUploadData>()

                        for (projDoc in projectSnapshot.documents) {
                            val idTopik = projDoc.getString("id_topik") ?: continue
                            val idProject = projDoc.getString("id_project")
                            val nilai = projDoc.getLong("nilai")?.toInt()
                            val imageUrl = projDoc.getString("imageUrl").orEmpty()
                            val status = projDoc.getBoolean("status")

                            projectMap[idTopik] = ProjectUploadData(
                                status, nilai, imageUrl, idTopik, idProject
                            )
                        }

                        // 4. Gabungkan topik + project_uploads
                        val mergedList = tempTopikList.map { topik ->
                            val uploadData = projectMap[topik.id]
                            topik.copy(
                                projectUpload = uploadData // pastikan TopikModel punya field ini
                            )

                        }

                        listData.clear()
                        listData.addAll(mergedList)

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

            if (data.projectUpload!=null){
                if (!data.projectUpload.imageUrl.isNullOrEmpty()){
                    gambarSoalProject = data.projectUpload.imageUrl
                }

                if (!data.projectUpload.id_project.isNullOrEmpty()){
                    dataIdProject = data.projectUpload.id_project
                }
            }

            // Gunakan warna biru jika data ada, abu-abu jika tidak
            val cardColor = if (data.projectUpload != null) {
                Color(0xFFC2D8FF) // biru
            } else {
                Color(0xFFE0E0E0) // abu
            }

            // Pilih ikon berdasarkan statusProject (atau idTopik, sesuai maksud kamu)
            val iconVector = when {
                data.projectUpload != null -> Icons.Default.PlayArrow
                else -> Icons.Default.Refresh
            }

            val iconBgColor = if (data.projectUpload != null) {
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
                        // Gunakan warna biru jika data ada, abu-abu jika tidak
                        if (data.projectUpload != null) {
                            isAddProject = true
                            topikModel = data

                        }
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
                                text = if (data.projectUpload!=null){
                                    if (data.projectUpload.status == true) "Sudah dinilai" else "Belum Dinilai"
                                } else "Belum dinilai",
                                fontFamily = poppinsfamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Text(
                                text = if (data.projectUpload!=null) {
                                    if (data.projectUpload.nilai!=null){
                                        "Nilai : ${data.projectUpload.nilai}"
                                    }else  "Nilai : -"

                                }else "Nilai : -",
                                fontFamily = poppinsfamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                        Text(
                            text = if (data.projectUpload!=null) {
                                if (!data.projectUpload.imageUrl.isNullOrEmpty() ){
                                    println("dindasayang ${data.projectUpload.imageUrl}")
                                    "Status : Sudah upload gambar"
                                }else  {"Status : Belum upload gambar"}

                            }else {"Status : Belum upload gambar"},
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

        },  kelas = kelas, program_keahlian = program_keahlian)

    }
}


@Preview(showBackground = true)
@Composable
private fun ProjectScreenPreview() {
    ProjectScreen(navController = rememberNavController(), semester = 0)

}

