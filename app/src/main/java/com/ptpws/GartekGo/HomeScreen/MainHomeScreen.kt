package com.ptpws.GartekGo.HomeScreen

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

import com.ptpws.GartekGo.Semester.ProjectScreen

@Composable
fun MainHomeScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val items = listOf("Beranda", "Nilai", "Profile")
    Scaffold(bottomBar = { MyBottomAppBar(navController = navController) }) { innerPadding ->
        NavHost(
            navController,
            startDestination = "beranda",
            modifier = Modifier.padding(innerPadding).fillMaxSize().background(color = Color(0xffF5F9FF))
        ) {
            composable("beranda") { HomeScreen(navController) }
            composable("nilai") { ProjectScreen(navController) }
            composable("profil") { ProfileScreen(navController) }
        }

        Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(color = Color(0xffF5F9FF))) {
            HomeScreen(navController)
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun MainHomeScreenPreview() {
    MainHomeScreen()

}