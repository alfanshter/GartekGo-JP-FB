package com.ptpws.GartekGo.Admin

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAdmin() {
    Scaffold(//topbar start
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "ADMIN",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = poppinsfamily,
                            color = Color.Black
                        )
                    },

                    )

            }
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color(0xffF5F9FF))
        ) {
            item {
                Column(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.admin),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(381.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column {
                            Card(
                                modifier = Modifier.size(130.dp),
                                shape = RoundedCornerShape(45.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.topik),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            Text("Topik", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Color.Black, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))

                        }

                        Column {
                            Card(
                                modifier = Modifier.size(130.dp),
                                shape = RoundedCornerShape(45.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.belajar),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            Text("Pembelajaran", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Color.Black, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))


                        }

                    }
                    Spacer(Modifier.height(34.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column {
                            Card(
                                modifier = Modifier.size(130.dp),
                                shape = RoundedCornerShape(45.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.project),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            Text("Project", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Color.Black, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))

                        }

                        Column {
                            Card(
                                modifier = Modifier.size(130.dp),
                                shape = RoundedCornerShape(45.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.siswa),
                                        contentDescription = null,
                                        tint = Color.Unspecified
                                    )
                                }
                            }
                            Text("Siswa", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Color.Black, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))


                        }

                    }



                }

            }


        }

    }


}

@Preview(showBackground = true)
@Composable
private fun HomeAdminPreview() {
    HomeAdmin()

}