package com.ptpws.GartekGo.Admin.Dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@Composable
fun PenilaianGambarDialog(
) {
    var showDialogpenilaiangambar by remember { mutableStateOf(true) }
    var tulisnilaiText by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }

    if (showDialogpenilaiangambar) {
        Dialog(
            onDismissRequest = { showDialogpenilaiangambar = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                // Card utama
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(723.dp)
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gambartugas),
                            contentDescription = null,
                            modifier = Modifier
                                .size(300.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                        )
                        Spacer(Modifier.height(23.dp))
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .height(48.dp)
                                .width(304.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff464646))
                        ) {
                            Text(
                                "Unduh Gambar",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                        Spacer(Modifier.height(23.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Nilai",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = Color.Black
                            )
                        }
                        Spacer(Modifier.height(4.dp))

                        TextField(
                            value = tulisnilaiText,
                            onValueChange = { tulisnilaiText = it },
                            placeholder = {
                                (Text(
                                    "Tulis Nilai",
                                    fontFamily = poppinsfamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray
                                ))
                            },
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B1B2F)
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                        )
                        Spacer(Modifier.height(23.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Keterangan",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp,
                                color = Color.Black
                            )
                        }
                        Spacer(Modifier.height(4.dp))

                        TextField(
                            value = keterangan,
                            onValueChange = { keterangan = it },
                            placeholder = {
                                (Text(
                                    "Tulis Keterangan",
                                    fontFamily = poppinsfamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray
                                ))
                            },
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {

                            Button(
                                onClick = {
                                    // Simpan data di sini jika perlu

                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF00C853
                                    )
                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.width(120.dp).height(60.dp)
                            ) {
                                Text(
                                    "SIMPAN",
                                    fontFamily = poppinsfamily,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Tombol Close bulat di luar card
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (-20).dp)
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(36.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        IconButton(onClick = { showDialogpenilaiangambar = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Tutup",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun NamaTopikDialogPreview() {
    PenilaianGambarDialog()

}