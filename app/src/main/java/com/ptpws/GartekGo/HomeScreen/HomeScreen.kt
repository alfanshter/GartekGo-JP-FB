import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.mulishfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp).background(color = Color(0xffF5F9FF)), horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                // Foto profil bulat
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Hai, Alfan Nurdin ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF202244), // Warna teks seperti di gambar
                        fontFamily = jostfamily // Ganti jika pakai font lain
                    )

                    Text(
                        text = "Apa yang ingin Anda pelajari hari ini? Cari di bawah.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontFamily = mulishfamily, fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(36.dp))
            Card(
                modifier = Modifier
                    .width(360.dp)
                    .height(89.dp), shape = RoundedCornerShape(22.dp),
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
                        fontSize = 36.sp, color = Color.White
                    )

                }

            }
            Spacer(Modifier.height(36.dp))
            Card(
                modifier = Modifier
                    .width(360.dp)
                    .height(89.dp), shape = RoundedCornerShape(22.dp),
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
                        fontSize = 36.sp, color = Color.White
                    )

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
                //card progres start

                Card(
                    modifier = Modifier
                        .padding(end = 34.dp)
                        .fillMaxWidth()
                        .height(167.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE1EBFF) // Biru muda
                    )
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Semester 1",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = poppinsfamily, color = Color.Black
                            )
                            Text(
                                "Topik 1",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = poppinsfamily, color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Progress Bar Material 3
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f) // Tetap 50% seperti keinginanmu
                                    .height(6.dp)
                                    .background(Color.White, RoundedCornerShape(3.dp)) // shape di sini
                            ) {
                                LinearProgressIndicator(
                                    progress = { 0.7f },
                                    modifier = Modifier
                                        .fillMaxSize(), // penuh di dalam box
                                    color = Color(0xFF337DFF),
                                    trackColor = Color.Transparent // tidak bentrok background
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Tahap + daftar tahap
                            Column {
                                Text(
                                    "Tahap",
                                    fontSize = 14.sp,
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xff1F1F39)
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "Materi ",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF337DFF),
                                        fontSize = 20.sp, fontFamily = poppinsfamily
                                    )

                                    Text(
                                        "Video ",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF337DFF),
                                        fontSize = 20.sp, fontFamily = poppinsfamily
                                    )

                                    Text(
                                        "Soal",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        fontSize = 20.sp, fontFamily = poppinsfamily
                                    )

                                    Text(
                                        " (Terkunci)",
                                        color = Color.Black,
                                        fontSize = 12.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                        }
                        // Play Button

                        IconButton(
                            onClick = { /* aksi */ },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top =12.dp, end = 24.dp)
                                .size(36.dp)
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
                //card progres end

            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()

}