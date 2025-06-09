import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ptpws.GartekGo.Commond.poppinsfamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateriScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier, containerColor = Color.White, topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Topik 1 : MATERI",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* back */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White // TopAppBar background
            )
        )

    }
    ) { innerPadding ->
                Spacer(Modifier.height(40.dp))
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    LazyColumn( contentPadding = innerPadding,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White)) {
                        item {
                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                val (textContent, button) = createRefs()

                                Text(
                                    text = "Binar Academy — Binarian tentu sudah tidak asing lagi dengan istilah UI/UX. UI sendiri merupakan singkatan dari User Interface dan UX adalah singkatan dari User Experience. Walaupun UI/UX merupakan istilah yang sering digunakan, bukan berarti keduanya memiliki fungsi yang sama. Baik UI maupun UX sama-sama berfungsi untuk mengembangkan desain produk, tetapi cara kerjanya yang berbeda. Untuk lebih lengkapnya, simak perbedaan UI dan UX di bawah ini yuk!\n\n" +
                                            "Apa itu UI Design?\n" +
                                            "Mudahnya, UI Design adalah tampilan produk yang ingin kita perlihatkan (yang visible atau bisa dilihat oleh mata). UI Designer lebih fokus pada visualisasi, coloring, dan hal-hal yang berkaitan dengan kreativitas dari interface yang akan digunakan oleh user.\n\n" +
                                            "Apa itu UX Design?\n" +
                                            "Nah, bagaimana dengan UX Design? UX Design memiliki ranah yang lebih luas dari UI. UX berfokus pada proses pembuatan produk hingga mampu mendapatkan pengalaman kemudahan dari user. Tanggung jawab seorang UX Designer adalah memastikan bahwa langkah demi langkah berjalan dengan logis dan jelas, serta memahami kebutuhan user.\n\n" +
                                            "Banyak yang beranggapan bahwa UX Design adalah visual yang terlihat dalam sebuah aplikasi. Padahal, UX Design jauh lebih dalam dari sekadar merancang visual untuk interface.\n\n" +
                                            "Memang, UI punya peran penting dalam karya seorang UX Designer, tetapi itu bukan satu-satunya bagian. Mengapa? Karena tujuan dari UX adalah mencari solusi dari sebuah masalah, dan interface produk tidak selalu menjadi solusinya.",
                                    fontSize = 12.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Normal,
                                    color = Color.DarkGray,
                                    modifier = Modifier.constrainAs(textContent) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(button.top, margin = 16.dp)
                                        width = Dimension.fillToConstraints
                                    }
                                )

                                Button(
                                    onClick = { /* TODO: aksi */ },
                                    shape = RoundedCornerShape(50),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF005DFF),
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier
                                        .constrainAs(button) {
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                        .fillMaxWidth()
                                        .height(60.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        // Teks di tengah
                                        Text(
                                            text = "SELANJUTNYA",
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.align(Alignment.Center)
                                        )

                                        // Icon Card bulat di kanan dengan jarak dari teks (91.dp kurang lebih, kita pakai align + padding)
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 8.dp) // padding agar tidak mentok kanan
                                                .size(48.dp)
                                        ) {

                                                Card(
                                                    shape = CircleShape,
                                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Box(
                                                        contentAlignment = Alignment.Center,
                                                        modifier = Modifier.fillMaxSize()
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.ArrowForward,
                                                            contentDescription = "Masuk",
                                                            tint = Color(0xFF0057FF),
                                                            modifier = Modifier.size(25.dp)
                                                        )
                                                    }

                                            }
                                        }
                                    }

                                }
                            }

                        }
                    }

                }
            }

}




@Preview(showBackground = true)
@Composable
private fun MateriScreenPreview() {
    MateriScreen()

}