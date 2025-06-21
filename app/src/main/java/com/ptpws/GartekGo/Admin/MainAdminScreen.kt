package com.ptpws.GartekGo.Admin

import HomeScreen
import MyBottomAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.Admin.Pages.HomeAdmin
import com.ptpws.GartekGo.Admin.Pages.PenilaianScreen
import com.ptpws.GartekGo.Admin.Pages.PenilaianSiswa
import com.ptpws.GartekGo.Admin.Pages.TambahMateriScreen
import com.ptpws.GartekGo.Admin.Pages.TambahPembelajaranScreen
import com.ptpws.GartekGo.Admin.Pages.TambahProjectMenu
import com.ptpws.GartekGo.Admin.Pages.TambahProjectScreen
import com.ptpws.GartekGo.Admin.Pages.TambahSoalScreen
import com.ptpws.GartekGo.Admin.Pages.TambahTopikScreen
import com.ptpws.GartekGo.Admin.Pages.TambahVidioScreen
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.HomeScreen.ProfileScreen
import com.ptpws.GartekGo.Semester.ProjectScreen

@Composable
fun MainAdminScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { MyBottomAppBarAdmin(navController = navController) }, modifier = Modifier.background(color = Color(0xffF5F9FF))) { innerPadding ->
        NavHost(
            navController,
            startDestination = "beranda",
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(color = Color(0xffF5F9FF))
        ) {
            composable("beranda") { HomeAdmin(navController) }
            composable("nilai") { ProjectScreen(navController) }
            composable("profil") { ProfileScreen(navController) }
            composable(AppScreen.Home.Admin.TambahTopik.route) { TambahTopikScreen(navController) }
            composable(AppScreen.Home.Admin.TambahPembelajaran.route) { TambahPembelajaranScreen(navController) }
            composable(AppScreen.Home.Admin.TambahMateri.route) { TambahMateriScreen(navController) }
            composable(AppScreen.Home.Admin.TambahVidio.route) { TambahVidioScreen(navController) }
            composable(AppScreen.Home.Admin.TambahSoal.route) { TambahSoalScreen(navController) }
            composable(AppScreen.Home.Admin.TambahProject.route) { TambahProjectMenu(navController) }
            composable(AppScreen.Home.Admin.TambahSiswa.route) { PenilaianSiswa(navController) }
            composable(AppScreen.Home.Admin.TambahSoalProject.route) { TambahProjectScreen(navController) }
            composable(AppScreen.Home.Admin.Penilaian.route) { PenilaianScreen(navController) }

        }




    }

}

@Preview
@Composable
private fun MainAdminScreenPreview() {
    MainAdminScreen()

}