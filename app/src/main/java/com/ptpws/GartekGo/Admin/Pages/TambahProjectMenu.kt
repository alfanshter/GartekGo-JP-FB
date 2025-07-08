package com.ptpws.GartekGo.Admin.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahProjectMenu(navController: NavController) {
    Scaffold(modifier = Modifier, containerColor = Color(0xffF5F9FF), topBar = {
        CenterAlignedTopAppBar(
            windowInsets = WindowInsets(0),
            title = {
                Text(
                    text = "Project",
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
    }, contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xffF5F9FF))
                .padding(start = 35.dp, end = 34.dp, top = 43.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .width(360.dp).clickable{navController.navigate(AppScreen.Home.Admin.TambahSoalProject.route)}
                        .height(129.dp), shape = RoundedCornerShape(23.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xffC2D8FF))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tambahmaterii),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(91.dp)
                        )
                        Spacer(Modifier.width(19.dp))

                        Text(
                            "SOAL",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            color = Color.Black
                        )
                    }
                }
                Spacer(Modifier.height(17.dp))
                Card(
                    modifier = Modifier
                        .width(360.dp).clickable{navController.navigate(AppScreen.Home.Admin.Penilaian.route)}
                        .height(129.dp), shape = RoundedCornerShape(23.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xffC2D8FF))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.penilaiann),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.size(91.dp)
                        )
                        Spacer(Modifier.width(17.dp))

                        Text(
                            "PENILAIAN",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color.Black
                        )
                    }
                }


            }
        }


    }

}

@Preview(showBackground = true)
@Composable
private fun TambahProjectMenuPreview() {
    TambahProjectMenu(navController = rememberNavController())

}