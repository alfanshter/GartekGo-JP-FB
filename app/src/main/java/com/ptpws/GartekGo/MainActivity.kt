package com.ptpws.GartekGo

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.ptpws.GartekGo.Admin.MainAdminScreen
import com.ptpws.GartekGo.Admin.Pages.HomeAdmin
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
        FirebaseApp.initializeApp(this)
        setContent {
            GartekGoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val context = LocalContext.current
                    val cameraPermissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) { isGranted ->
                        if (!isGranted) {
                            Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Cek dan minta izin saat pertama kali
                    LaunchedEffect(Unit) {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    }

                  MainHomeScreen()
                }
            }
        }
    }
}
