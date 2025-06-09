package com.ptpws.GartekGo.BottomNavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@Composable
fun BottomNavigation(modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf("Beranda", "Nilai", "Profile")
    val icons = listOf(
        R.drawable.home,
        R.drawable.piala,
        R.drawable.profilenav
    )


    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White), // Background putih
        containerColor = Color.White // Warna background navigation bar
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedItem == index

            NavigationBarItem(
                selected = isSelected,
                onClick = { selectedItem = index },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = item,
                            tint = if (isSelected) Color(0xFF0961F5) else Color(0xFFB0B0B0), // aktif = biru, tidak aktif = abu
                            modifier = Modifier.size(33.dp)
                        )
                        Text(
                            text = item,
                            color = if (isSelected) Color(0xFF000000) else Color(0xFFB0B0B0),
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp,

                            )
                    }
                },
                alwaysShowLabel = true, // wajib true supaya label muncul
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent // hilangkan efek oval
                )
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationPreview() {
    BottomNavigation()

}