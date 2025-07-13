package com.ptpws.GartekGo.Admin.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAdmin(navController: NavController,  outerPadding: PaddingValues = PaddingValues()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        Scaffold(
            //topbar start
            topBar = {
                Column {
                    CenterAlignedTopAppBar(
                        windowInsets = WindowInsets(0),
                        title = {
                            Text(
                                text = "ADMIN",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                fontFamily = poppinsfamily,
                                color = Color.Black
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color(0xffF5F9FF)
                        )
                    )

                }
            }, contentWindowInsets = WindowInsets(0),
        ) { innerPadding ->
            val combinedPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = outerPadding.calculateBottomPadding()
            )
            Column(
                modifier = Modifier
                    .padding(combinedPadding)
                    .fillMaxSize()
                    .background(color = Color(0xffF5F9FF))
            ) {
                HomeAdminComponent(navController = navController)
                // Tab Row
                // TAB ROW DILETAKKAN DI SINI, DI BAWAH TOPBAR



                // LazyColumn berisi semua konten termasuk tombol
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun HomeAdminPreview() {
    HomeAdmin(navController = rememberNavController())

}

@Composable
fun HomeAdminComponent(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        item {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.admin),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(320.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Card(
                            modifier = Modifier.size(130.dp).clickable{ navController.navigate(
                                AppScreen.Home.Admin.TambahTopik.route)},
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
                        Text(
                            "Topik",
                            fontFamily = jostfamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )

                    }

                    Column {
                        Card(
                            modifier = Modifier.size(130.dp).clickable{ navController.navigate(
                                AppScreen.Home.Admin.TambahPembelajaran.route) },
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
                        Text(
                            "Pembelajaran",
                            fontFamily = jostfamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )


                    }

                }
                Spacer(Modifier.height(34.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Card(
                            modifier = Modifier.size(130.dp).clickable{navController.navigate(
                                AppScreen.Home.Admin.TambahProject.route)},
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
                        Text(
                            "Project",
                            fontFamily = jostfamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )

                    }

                    Column {
                        Card(
                            modifier = Modifier.size(130.dp).clickable{navController.navigate(
                                AppScreen.Home.Admin.TambahSiswa.route)},
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
                        Text(
                            "Siswa",
                            fontFamily = jostfamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                        )


                    }

                }


            }

        }


    }

}