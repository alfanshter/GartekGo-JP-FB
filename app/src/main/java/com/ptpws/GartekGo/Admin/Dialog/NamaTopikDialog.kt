package com.ptpws.GartekGo.Admin.Dialog

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ptpws.GartekGo.Commond.poppinsfamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamaTopikDialog( onDismis: () -> Unit) {
    var topicText by remember { mutableStateOf("") }


        AlertDialog(
            onDismissRequest = {onDismis()  },
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
                        .height(300.dp)
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {

                        TextField(
                            value = topicText,
                            onValueChange = { topicText = it },
                            placeholder = {
                                (Text(
                                    "Nama Topik",
                                    fontFamily = poppinsfamily,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray
                                ))
                            },
                            shape = RoundedCornerShape(24.dp),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 18.sp,
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
                                .height(106.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    topicText = ""
                                    onDismis()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.width(120.dp)
                            ) {
                                Text("HAPUS", fontFamily = poppinsfamily, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            }

                            Button(
                                onClick = {
                                    // Simpan data di sini jika perlu
                                    onDismis()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF00C853
                                    )
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.width(120.dp)
                            ) {
                                Text("SIMPAN",fontFamily = poppinsfamily, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
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
                        IconButton(onClick = { onDismis()  }) {
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




@Preview(showBackground = true)
@Composable
private fun NamaTopikDialogPreview() {
    NamaTopikDialog (onDismis = { /*TODO*/ })

}
