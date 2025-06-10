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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@Composable
fun ProjectScreen( ) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 21.dp).background(color = Color.White)) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth().height(129.dp).padding(start = 34.dp, end = 34.dp)
                    ,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC2D8FF)),
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
                            text = "Topik 1 – Belajar Membaca",
                            fontFamily = poppinsfamily, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Status : Dikonfirmasi Guru",
                            fontFamily = poppinsfamily, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "Nilai : 90",
                            fontFamily = poppinsfamily, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    IconButton(
                        onClick = { /* TODO: Aksi Play */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = Color(0xFF3366FF), shape = CircleShape)
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
                    .fillMaxWidth().height(129.dp).padding(start = 34.dp, end = 34.dp)
                ,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD2D2D2)),
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
                            text = "Topik 2 – Belajar Menulis",
                            fontFamily = poppinsfamily, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Status : Sudah Mengumpulkan",
                            fontFamily = poppinsfamily, fontSize = 15.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Text(
                            text = "Nilai : -",
                            fontFamily = poppinsfamily, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    IconButton(
                        onClick = { /* TODO: Aksi Play */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = Color(0xFF009A0F), shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.loading),
                            contentDescription = "Play",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth().height(129.dp).padding(start = 34.dp, end = 34.dp)
                ,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD2D2D2)),
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
                            text = "Topik 3 – Belajar Menghitung",
                            fontFamily = poppinsfamily, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Status : -",
                            fontFamily = poppinsfamily, fontSize = 15.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Text(
                            text = "Nilai : -",
                            fontFamily = poppinsfamily, fontSize = 16.sp, fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    IconButton(
                        onClick = { /* TODO: Aksi Play */ },
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = Color(0xFFCCCCCC), shape = CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "Play",
                            tint = Color.Unspecified
                        )
                    }
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ProjectScreenPreview() {
    ProjectScreen()

}