package com.ptpws.GartekGo.Admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@Composable
fun MyBottomAppBarAdmin(navController: NavController) {
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    // Atur warna Navigation Bar Sistem
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color(0xffF5F9FF),
            darkIcons = true
        )
    }

    BottomAppBar(
        containerColor = Color.White
    ) {
        // Beranda
        IconButton(
            onClick = {
                selected.value = Icons.Default.Home
                navController.navigate("beranda") {
                    popUpTo(0)
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Beranda",
                    modifier = Modifier.size(24.dp),
                    tint = if (selected.value == Icons.Default.Home)
                        colorResource(id = R.color.biru)
                    else
                        colorResource(R.color.abuabu)
                )
                Text(
                    text = "Beranda",
                    fontSize = 11.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Medium,
                    color = if (selected.value == Icons.Default.Home)
                        colorResource(id = R.color.biru)
                    else
                        colorResource(R.color.abuabu)
                )
            }
        }

        // Nilai
        IconButton(
            onClick = {
                selected.value = Icons.Default.AddCard
                navController.navigate("nilai") {
                    popUpTo(0)
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.piala),
                    contentDescription = "Nilai",
                    modifier = Modifier.size(24.dp),
                    tint = if (selected.value == Icons.Default.AddCard)
                        colorResource(id = R.color.biru)
                    else
                        colorResource(R.color.abuabu)
                )
                Text(
                    text = "Nilai",
                    fontSize = 11.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Medium,
                    color = if (selected.value == Icons.Default.AddCard)
                        colorResource(id = R.color.biru)
                    else
                        colorResource(R.color.abuabu)
                )
            }
        }

        // Profil
        IconButton(
            onClick = {
                selected.value = Icons.Default.Notifications
                navController.navigate("profil") {
                    popUpTo(0)
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.profilenav),
                    contentDescription = "Profil",
                    modifier = Modifier.size(24.dp),
                    tint = if (selected.value == Icons.Default.Notifications)
                        colorResource(id = R.color.biru)
                    else
                        colorResource(R.color.abuabu)
                )
                Text(
                    text = "Profil",
                    fontSize = 11.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Medium,
                    color = if (selected.value == Icons.Default.Notifications)
                        colorResource(id = R.color.biru)
                    else
                        colorResource(R.color.abuabu)
                )
            }
        }
    }
}
