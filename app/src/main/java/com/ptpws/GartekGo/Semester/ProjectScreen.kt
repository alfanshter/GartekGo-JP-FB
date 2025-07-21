package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.ptpws.GartekGo.Admin.model.TopikModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@Composable
fun ProjectScreen(navController: NavController) {
    val db = Firebase.firestore
    val listData = remember { mutableStateListOf<TopikModel>() }
    val userUid = FirebaseAuth.getInstance().uid

    // Map: idTopik -> Pair(status, nilai)
    val projectUploadMap = remember { mutableStateMapOf<String, Pair<String?, Int?>>() }

    LaunchedEffect(Unit) {
        val getdata = db.collection("topik").orderBy("nomor").get()
        getdata.addOnSuccessListener { data ->
            listData.clear()
            for (datas in data.documents) {
                val topik = datas.toObject(TopikModel::class.java)
                if (topik != null) {
                    listData.add(topik.copy(id = datas.id))
                }
            }
        }

        db.collection("project_uploads")
            .whereEqualTo("uid", userUid)
            .get().addOnSuccessListener { snapshot ->
                for (projDoc in snapshot.documents) {
                    val idTopik = projDoc.getString("id_topik")
                    val nilai = projDoc.getLong("nilai")?.toInt()
                    val status = projDoc.getString("status")

                    if (idTopik != null) {
                        projectUploadMap[idTopik] = Pair(status, nilai)
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

            val projectPair = projectUploadMap[data.id]
            val statusProject = projectPair?.first
            val nilaiProject = projectPair?.second

            val cardColor = if (statusProject == "Dikonfirmasi Guru") {
                Color(0xFFC2D8FF)
            } else if (statusProject == "Sudah Mengumpulkan") {
                Color(0xFFE0E0E0)
            } else {
                Color(0xFFD2D2D2)
            }

            val iconVector = if (statusProject == "Dikonfirmasi Guru") {
                Icons.Default.PlayArrow
            } else if (statusProject == "Sudah Mengumpulkan") {
                Icons.Default.Refresh
            } else {
                Icons.Default.Lock
            }

            val iconBgColor = if (statusProject == "Dikonfirmasi Guru") {
                Color(0xFF3366FF)
            } else if (statusProject == "Sudah Mengumpulkan") {
                Color(0xFF00AA00)
            } else {
                Color(0xFFCCCCCC)
            }

            val iconTint = if (statusProject == "Dikonfirmasi Guru" || statusProject == "Sudah Mengumpulkan") {
                Color.White
            } else {
                Color.Black
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .padding(start = 34.dp, end = 34.dp),
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
                        Text(
                            text = "Topik ${data.nomor} - ${data.nama ?: ""}",
                            fontFamily = poppinsfamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Status : ${statusProject ?: "-"}",
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

                    IconButton(
                        onClick = { /* TODO: Aksi Play */ },
                        enabled = statusProject == "Dikonfirmasi Guru",
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
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun ProjectScreenPreview() {
    ProjectScreen(navController = rememberNavController())

}


//  Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(129.dp)
//                    .padding(start = 34.dp, end = 34.dp),
//                colors = CardDefaults.cardColors(containerColor = Color(0xFFD2D2D2)),
//                shape = RoundedCornerShape(16.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Row(
//                    modifier = Modifier.padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Column(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "Topik 2 – Belajar Menulis",
//                            fontFamily = poppinsfamily,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = "Status : Sudah Mengumpulkan",
//                            fontFamily = poppinsfamily,
//                            fontSize = 15.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black,
//                        )
//                        Text(
//                            text = "Nilai : -",
//                            fontFamily = poppinsfamily,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                    }
//
//                    IconButton(
//                        onClick = { /* TODO: Aksi Play */ },
//                        modifier = Modifier
//                            .size(40.dp)
//                            .background(color = Color(0xFF009A0F), shape = CircleShape)
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.loading),
//                            contentDescription = "Play",
//                            tint = Color.White
//                        )
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(12.dp))
//
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(129.dp)
//                    .padding(start = 34.dp, end = 34.dp),
//                colors = CardDefaults.cardColors(containerColor = Color(0xFFD2D2D2)),
//                shape = RoundedCornerShape(16.dp),
//                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//            ) {
//                Row(
//                    modifier = Modifier.padding(16.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Column(
//                        modifier = Modifier.weight(1f)
//                    ) {
//                        Text(
//                            text = "Topik 3 – Belajar Menghitung",
//                            fontFamily = poppinsfamily,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Text(
//                            text = "Status : -",
//                            fontFamily = poppinsfamily,
//                            fontSize = 15.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black,
//                        )
//                        Text(
//                            text = "Nilai : -",
//                            fontFamily = poppinsfamily,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black
//                        )
//                    }
//
//                    IconButton(
//                        onClick = { /* TODO: Aksi Play */ },
//                        modifier = Modifier
//                            .size(40.dp)
//                            .background(color = Color(0xFFCCCCCC), shape = CircleShape)
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.lock),
//                            contentDescription = "Play",
//                            tint = Color.Unspecified
//                        )
//                    }
//                }
//            }