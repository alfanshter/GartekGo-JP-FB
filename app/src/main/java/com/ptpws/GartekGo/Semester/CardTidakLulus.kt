package com.ptpws.GartekGo.Semester

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.mulishfamily
import com.ptpws.GartekGo.R

@Composable
fun CardTidakLulus(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .width(360.dp)
            .height(490.dp),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Gambar (silakan ganti dengan asset sendiri)
            Icon(
                painter = painterResource(id = R.drawable.nub), // Ganti 'your_image' sesuai gambar kamu
                contentDescription = "Icon", tint = Color.Unspecified,
                modifier = Modifier
                    .height(184.dp).width(200.dp)
                    .padding(bottom = 8.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Nilai 100",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121), fontFamily = jostfamily
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Selamat! Anda telah menyelesaikan kuis topik ini.\n" +
                            "Silakan lanjutkan dengan mengerjakan Project\n" +
                            "Gambar Manual untuk Topik 1 di menu sebelah.",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color(
                        0xff545454
                    ), fontFamily = mulishfamily, fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "REVIEW",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF167F71), fontFamily = jostfamily,
                    modifier = Modifier
                        .clickable {  }
                )
            }

            //tombol lanjut

            Button(
                onClick = { /* TODO: Aksi tombol */ },
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00AA13), // Hijau seperti di gambar
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(206.dp)
                    .height(60.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Lanjut Gambar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = jostfamily,
                        color = Color.White
                    )
                    Spacer(Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.White, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Lanjut",
                            tint = Color(0xFF00AA13),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }




        }
    }


}

@Preview(showBackground = true)
@Composable
private fun CardTidakLulusPreview() {
    CardTidakLulus()

}