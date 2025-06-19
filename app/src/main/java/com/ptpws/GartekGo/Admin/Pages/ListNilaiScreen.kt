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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ListNilaiScreen(navController: NavController) {
    val chipLabelsnilai =
        listOf("Chip Filter", "Chip Filter", "Chip Filter", "Chip Filter", "Chip Filter")
    var selectedChipnilaiIndex by remember { mutableStateOf(1) } // index chip yang aktif
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Nilai",
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF5F9FF))
                .padding(innerPadding)
        ) {
            FlowRow(
                modifier = Modifier.padding(16.dp).background(color = Color(0xffF5F9FF)),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                chipLabelsnilai.forEachIndexed { index, label ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (selectedChipnilaiIndex == index) Color(0xFF2962FF) else Color.Gray
                        ),
                        color = Color.Transparent,
                        modifier = Modifier
                            .height(23.dp)
                            .clickable { selectedChipnilaiIndex = index }
                    ) {
                        Text(
                            text = label,
                            fontWeight = if (selectedChipnilaiIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontFamily = poppinsfamily,
                            fontSize = 11.sp,
                            color = if (selectedChipnilaiIndex == index) Color(0xFF2962FF) else Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            //lazycontent
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF5F9FF))) {
                item {
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
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
                                    text = "Cristiano Ronaldo",
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
                                    text = " ronaldo7@goat.com",
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
                                    text = "7",
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
                                    text = "7 TKJ 1",
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
                                    text = "Goal Poacher",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                            }



                        }
                    }
                    Spacer(Modifier.height(7.dp))

                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()) {
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(
                                    text = "Nama :",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Lamine Yamal",
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
                                    text = "yamalquefue@jbl.com",
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
                                    text = "19",
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
                                    text = "10 TKJ 1",
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
                                    text = "Baby Goat",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
                            }




                        }
                    }
                    Spacer(Modifier.height(7.dp))

                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()) {
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text(
                                    text = "Nama :",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = " Leonel Messi",
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
                                    fontSize = 16.sp, color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "  messi10@goat.com",
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
                                    text = "10",
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
                                    fontSize = 16.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = " 10 TKJ 1",
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
                                    fontSize = 16.sp, color = Color.Black
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = " Goat",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp, color = Color.Black
                                )
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
private fun ListNilaiScreenPreview() {
    ListNilaiScreen(navController = rememberNavController())
}
