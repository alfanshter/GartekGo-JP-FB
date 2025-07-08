package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopikProject(navController: NavController) {

    Scaffold(modifier = Modifier, containerColor = Color(0xffC2D8FF), topBar = {
        CenterAlignedTopAppBar(
            windowInsets = WindowInsets(0),
            title = {
                Text(
                    text = "Topik 1 : PROJECT",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    fontFamily = poppinsfamily,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack() }) {
                    Icon(painter = painterResource(id = R.drawable.back),contentDescription = null, tint = Color.Unspecified)

                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color(0xffF5F9FF) // TopAppBar background
            )
        )

    }, contentWindowInsets = WindowInsets(0)
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
                            text = "Lorem ipsum dolor sit amet, consectetur\n adipiscing elit. Curabitur ut lectus ac sem\n rhoncus lobortis at a nisl. Etiam pharetra\n vulputate tellus, non consectetur sem\n pellentesque a. Proin vestibulum\n elementum lectus et feugiat. Suspendisse\n potenti. Morbi consectetur odio a tortor\n pellentesque, at luctus erat congue. Cras\n sagittis tempus diam, eu rhoncus ipsum.\n Aenean elit risus, ullamcorper in augue\n sed, dictum dapibus leo. Sed sollicitudin\n tellus at ante commodo mattis. Lorem\n ipsum dolor sit amet, consectetur adipiscing elit.",
                            fontSize = 12.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center,
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
                            onClick = { navController.navigate(AppScreen.Home.Semester.Topik.Vidio.route) },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF009A0F),
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
                                                tint = Color(0xFF009A0F),
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
private fun TopikProjectPreview() {
    TopikProject(navController = rememberNavController())

}