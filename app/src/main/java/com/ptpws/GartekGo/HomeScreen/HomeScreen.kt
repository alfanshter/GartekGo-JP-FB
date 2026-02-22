import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ptpws.GartekGo.model.TopikModel
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.mulishfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@Composable
fun HomeScreen(navController: NavController) {
    var nama by remember { mutableStateOf("") }
    val uid = FirebaseAuth.getInstance().uid
    val listData = remember { mutableStateListOf<TopikModel>() }
    val db = Firebase.firestore

    // 🔥 Ambil nama user
    db.collection("users").document(uid.toString()).get()
        .addOnSuccessListener { data ->
            nama = data.get("nama").toString()
        }

    LaunchedEffect(Unit) {
        db.collection("topik").get()
            .addOnSuccessListener { data ->
                listData.clear()
                for (datas in data.documents) {
                    val topik = datas.toObject(TopikModel::class.java)
                    if (topik != null) {
                        listData.add(topik.copy(id = datas.id))
                    }
                }

                db.collection("users").document(uid.toString())
                    .collection("topik")
                    .get()
                    .addOnSuccessListener { snapshot ->
                        val topikMap = snapshot.documents.associateBy { it.id }

                        val gabungan = listData.map { topik ->
                            val userMeta = topikMap[topik.id]
                            topik.copy(
                                soal = userMeta?.getString("soal") ?: "0",
                                materi = userMeta?.getString("materi") ?: "0",
                                vidio = userMeta?.getString("vidio") ?: "0"
                            )
                        }

                        listData.clear()
                        listData.addAll(gabungan)
                    }
            }
    }

    // 🔥 Sort by nomor
    val sortedData = listData.sortedBy {
        try {
            it.nomor?.toInt() ?: Int.MAX_VALUE
        } catch (e: Exception) {
            Int.MAX_VALUE
        }
    }

    // 🔥 Ambil topik pertama yang belum selesai
    val nextTopik = sortedData.firstOrNull {
        it.materi == "0" || it.vidio == "0" || it.soal == "0"
    } ?: sortedData.lastOrNull()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .background(color = Color(0xffF5F9FF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        nextTopik?.let { data ->
            item {
                Icon(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_person_24),
                        contentDescription = "Foto Profil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Hai ",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF202244),
                                fontFamily = jostfamily
                            )
                            Text(
                                text = nama,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF202244),
                                fontFamily = jostfamily
                            )
                        }
                        Text(
                            text = "Apa yang ingin Anda pelajari hari ini? Cari di bawah.",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = mulishfamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(36.dp))
                Column(modifier = Modifier.fillMaxSize().padding(start = 34.dp, end = 34.dp)) {
                    Card(
                        modifier = Modifier
                            .width(360.dp)
                            .height(89.dp)
                            .clickable {
                                navController.navigate(AppScreen.Home.Semester.createRoute(semester = 1))
                            },
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xff0961F5))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Semester 1",
                                fontFamily = mulishfamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 36.sp,
                                color = Color.White
                            )
                        }
                    }
                    Spacer(Modifier.height(36.dp))
                    Card(
                        modifier = Modifier
                            .width(360.dp)
                            .height(89.dp)
                            .clickable {
                                navController.navigate(AppScreen.Home.Semester.createRoute(semester = 2))
                            },
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xff0961F5))
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Semester 2",
                                fontFamily = mulishfamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 36.sp,
                                color = Color.White
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 34.dp)
                ) {
                    Spacer(Modifier.height(45.dp))
                    Text(
                        "Sedang Dikerjakan",
                        fontFamily = mulishfamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        color = Color(0xff0961F5)
                    )
                    Spacer(Modifier.height(22.dp))
                    Card(
                        modifier = Modifier
                            .padding(end = 34.dp)
                            .fillMaxWidth()
                            .height(190.dp)
                            .clickable { navController.navigate("${AppScreen.Home.Semester.Topik.route}/${data.id}") },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFC2D8FF))
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                "Topik ${data.nomor} - ${data.nama}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                fontFamily = poppinsfamily,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 12.dp, end = 12.dp, bottom = 12.dp)
                                    .align(Alignment.TopStart)
                            )

                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(start = 16.dp, bottom = 6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.5f)
                                        .height(6.dp)
                                        .background(Color.White, RoundedCornerShape(3.dp))
                                ) {
                                    LinearProgressIndicator(
                                        progress = { 0.7f },
                                        modifier = Modifier.fillMaxSize(),
                                        color = Color(0xFF337DFF),
                                        trackColor = Color.Transparent
                                    )
                                }

                                Text(
                                    "Tahap",
                                    fontSize = 12.sp,
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xff1F1F39)
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "Materi ",
                                        fontWeight = FontWeight.Bold,
                                        color = if (data.materi == "1") Color(0xff0961F5) else Color.Black,
                                        fontSize = 14.sp,
                                        fontFamily = poppinsfamily
                                    )
                                    Text(
                                        "Video",
                                        color = if (data.materi == "1") Color(0xff0961F5) else Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = poppinsfamily
                                    )
                                    if (data.materi == "0") {
                                        Text(
                                            " (Terkunci) ",
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Text(
                                        "Soal",
                                        color = if (data.vidio == "1") Color(0xff0961F5) else Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = poppinsfamily
                                    )
                                    if (data.materi == "0") {
                                        Text(
                                            " (Terkunci)",
                                            color = Color.Black,
                                            fontSize = 10.sp,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = { /* aksi play */ },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(top = 12.dp, end = 24.dp)
                                    .size(32.dp)
                                    .background(Color(0xFF337DFF), shape = CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())

}