package com.ptpws.GartekGo.HomeScreen

import HomeScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ptpws.GartekGo.BottomNavigation.BottomNavigation

@Composable
fun MainHomeScreen(modifier: Modifier = Modifier) {
    Scaffold(bottomBar = { BottomNavigation() }) {
        innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            HomeScreen()
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun MainHomeScreenPreview() {
    MainHomeScreen()

}