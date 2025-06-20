package com.ptpws.GartekGo.Admin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ptpws.GartekGo.Admin.Pages.HomeAdmin
import com.ptpws.GartekGo.Admin.Pages.TambahPembelajaranScreen
import com.ptpws.GartekGo.Admin.Pages.TambahTopikScreen
import com.ptpws.GartekGo.AppScreen

@Composable
fun AdminNavhost(modifier: Modifier = Modifier) {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController,
        startDestination = AppScreen.Home.Admin.route) {

        composable(AppScreen.Home.Admin.route) { HomeAdmin(navigationController) }


    }

}