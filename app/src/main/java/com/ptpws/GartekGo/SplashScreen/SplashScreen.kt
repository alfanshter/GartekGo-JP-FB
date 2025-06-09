package com.ptpws.GartekGo.SplashScreen

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import com.ptpws.GartekGo.R

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xff0961F5)), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(id = R.drawable.gartekgo),
            contentDescription = null,
            tint = Color.Unspecified
        )

    }


}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()

}