import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopikScreen(navController: NavController) {
    var materi by remember { mutableStateOf("") }
    var vidio by remember { mutableStateOf("") }
    var soal by remember { mutableStateOf("") }

    var uid = FirebaseAuth.getInstance().currentUser!!.uid

    val db = Firebase.firestore

    val getdata =
        db.collection("users").document(uid).collection("topik").document("L1b3OsVNHV01boF5P0MB").get()
    getdata.addOnSuccessListener { data ->
        materi = data.get("materi").toString()
        vidio = data.get("vidio").toString()
        soal = data.get("soal").toString()

        Log.d("muhib",materi.toString())
    }
    Scaffold(modifier = Modifier, containerColor = Color(0xffF5F9FF), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Topik 1",
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
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xffF5F9FF) // TopAppBar background
            )
        )
    }
    ) { innerPadding ->

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF5F9FF))
                .padding(start = 35.dp, end = 34.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .width(359.dp)
                        .height(129.dp).clickable{ navController.navigate(AppScreen.Home.Semester.Topik.Materi.route)},
                    colors = CardDefaults.cardColors(containerColor = Color(0xffC2D8FF)),
                    shape = RoundedCornerShape(23.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            "MATERI 1",
                            fontFamily = poppinsfamily,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold, color = Color.Black
                        )
                        // Tombol Play tetap di pojok kanan atas
                        IconButton(
                            onClick = { /* aksi */ },
                            modifier = Modifier
                                .size(32.dp) // lebih kecil agar proporsional
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

                Card(
                    modifier = Modifier
                        .width(359.dp)
                        .height(129.dp),
                    shape = RoundedCornerShape(23.dp),
                    colors = CardDefaults.cardColors(containerColor
                    = if (materi == "1")Color(0xffC2D8FF)else
                        Color(0xFFDCDCDC)) // Abu-abu terang
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Text(
                                text = "VIDEO",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E1E2F) // Warna gelap
                                , fontFamily = poppinsfamily, fontSize = 36.sp

                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Baca Materi Terlebih Dahulu",
                                fontFamily = poppinsfamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF1E1E2F)

                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(44.dp)
                                .background(
                                    color = Color(0xFFE0E0E0), // Lingkaran abu-abu
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (materi == "1") {
                                IconButton(
                                    onClick = { /* aksi */ },
                                    modifier = Modifier
                                        .size(32.dp) // lebih kecil agar proporsional
                                        .background(Color(0xFF337DFF), shape = CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = Color.White
                                    )
                                }
                            }else{
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .size(44.dp)
                                        .background(
                                            color = Color(0xFFE0E0E0), // Lingkaran abu-abu
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Terkunci",
                                        tint = Color.Black
                                    )
                                }

                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .width(359.dp)
                        .height(129.dp),
                    shape = RoundedCornerShape(23.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDCDCDC)) // Abu-abu terang
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.CenterStart)
                        ) {
                            Text(
                                text = "SOAL",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E1E2F) // Warna gelap
                                , fontFamily = poppinsfamily, fontSize = 36.sp

                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tonton Video  Terlebih Dahulu",
                                fontFamily = poppinsfamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF1E1E2F)

                            )
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(44.dp)
                                .background(
                                    color = Color(0xFFE0E0E0), // Lingkaran abu-abu
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Terkunci",
                                tint = Color.Black
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
private fun TopikScreenPreview() {
    TopikScreen(navController = rememberNavController())

}