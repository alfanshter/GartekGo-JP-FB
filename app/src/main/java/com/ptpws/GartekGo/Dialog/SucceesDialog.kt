package com.ptpws.GartekGo.Dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.mulishfamily
import com.ptpws.GartekGo.R

@Composable
fun SuccessDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .width(280.dp), colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .background(Color.White)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // Ganti dengan gambar kamu sendiri
                    Icon(
                        painter = painterResource(id = R.drawable.sukses), // ganti dengan gambar kamu
                        contentDescription = "Success Icon",
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 16.dp), tint = Color.Unspecified
                    )

                    Text(
                        text = "SUKSES",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black, fontFamily = jostfamily
                    )

                    Text(
                        text = "Gambar berhasil dikirim",
                        fontSize = 14.sp,
                        color = Color(0xff545454),
                        modifier = Modifier.padding(top = 4.dp),
                        fontFamily = mulishfamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun SuccesDialogPreview() {
    SuccessDialog(onDismiss = { })

}
