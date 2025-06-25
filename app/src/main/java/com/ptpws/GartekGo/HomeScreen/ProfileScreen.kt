package com.ptpws.GartekGo.HomeScreen

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.ptpws.GartekGo.Auth.AuthActivity
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    var context = LocalContext.current

    //panggil inisialisasi Firebase Auth
    var database = FirebaseAuth.getInstance()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "PROFIL",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFF5F9FF)
                )
            )
        },
        containerColor = Color(0xFFF5F9FF)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .background(color = Color(0xffF5F9FF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(16.dp))
                // Card Foto Profil
                Card(
                    modifier = Modifier
                        .size(133.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profil Icon",
                        tint = Color.Black,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                // Informasi user di sisi kiri
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 27.dp)
                ) {
                    Text(
                        text = "Email",
                        fontFamily = poppinsfamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Program Keahlian",
                        fontFamily = poppinsfamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nomor Absen",
                        fontFamily = poppinsfamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kelas",
                        fontFamily = poppinsfamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {

                        database.signOut()

                        // 2. Pindah ke AuthActivity
                        val intent = Intent(context, AuthActivity::class.java)
                        context.startActivity(intent)

                        // 3. Tutup activity saat ini agar tidak bisa kembali
                        (context as? Activity)?.finish()

                    },
                    modifier = Modifier
                        .width(120.dp)
                        .height(41.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffF50909))
                ) {
                    Text(
                        "LOGOUT",
                        fontFamily = jostfamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 17.sp,
                        color = Color.White
                    )

                }

            }

            // Logo diganti Icon juga
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())

}