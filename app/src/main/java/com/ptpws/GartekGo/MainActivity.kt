package com.ptpws.GartekGo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.Admin.Pages.NilaiSiswa
import com.ptpws.GartekGo.Admin.Pages.PenilaianScreen
import com.ptpws.GartekGo.Admin.Pages.SoalListScreen
import com.ptpws.GartekGo.Auth.LoginScreen
import com.ptpws.GartekGo.HomeScreen.MainHomeScreen
import com.ptpws.GartekGo.Semester.SoalSelesaiScreen
import com.ptpws.GartekGo.Semester.UploadScreen
import com.ptpws.GartekGo.SplashScreen.SplashScreen
import com.ptpws.GartekGo.ui.theme.GartekGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GartekGoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    NilaiSiswa(navController = rememberNavController())
                }
            }
        }
    }
}
