package com.ptpws.GartekGo.Admin.Dialog

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahVidioDialog(onDismis: () -> Unit) {
    var vidioText by remember { mutableStateOf("") }
    var selectedTopik by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var linkvidio by remember { mutableStateOf("") }

    val topikList = listOf("Topik 1", "Topik 2", "Topik 3")

        AlertDialog(
            onDismissRequest = { onDismis() },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        // Nama Materi
                        TextField(
                            value = vidioText,
                            onValueChange = { vidioText = it },
                            placeholder = {
                                Text(
                                    "Nama vidio",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            },
                            textStyle = TextStyle(
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Upload File ()
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Link Vidio",
                                fontFamily = poppinsfamily,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xff020202),
                                fontSize = 12.sp
                            )

                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextField(
                                    value = linkvidio,
                                    onValueChange = { linkvidio = it },
                                    placeholder = {
                                        Text(
                                            "Masukkan Link",
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp
                                        )
                                    },
                                    textStyle = TextStyle(
                                        fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    ),
                                    shape = RoundedCornerShape(10.dp),
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(), // memberi ruang untuk ikon hapus
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )

                                // Icon hapus
                                if (linkvidio.isNotEmpty()) {
                                    IconButton(
                                        onClick = { linkvidio = "" },
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 8.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Hapus teks", tint = Color.Black
                                        )
                                    }
                                }
                            }

                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Pilih Topik
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = selectedTopik,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = {
                                    Text(
                                        "Pilih Topik",
                                        fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp
                                    )
                                },
                                textStyle = TextStyle(
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.tambahdrop), // panah kiri
                                        contentDescription = null, tint = Color.Unspecified,
                                        modifier = Modifier
                                            .rotate(if (expanded) 90f else 0f) // rotasi ke bawah saat expanded
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                            ) {
                                topikList.forEach { topik ->
                                    DropdownMenuItem(
                                        text = { Text(topik,
                                                fontFamily = poppinsfamily,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 16.sp,
                                                color = Color.Black
                                            ) },
                                        onClick = {
                                            selectedTopik = topik
                                            expanded = false
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Tombol aksi
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { onDismis() },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.width(120.dp)
                            ) {
                                Text("HAPUS",fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp, color = Color.White)
                            }

                            Button(
                                onClick = { onDismis() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF00C853
                                    )
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.width(120.dp)
                            ) {
                                Text(
                                    "SIMPAN",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                // Tombol close (X)
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
                        IconButton(onClick = { onDismis() }) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color.Black)
                        }
                    }
                }
            }
        }
    }



@Preview
@Composable
private fun TambahVidioDialogPreview() {
    TambahVidioDialog(onDismis = { /*TODO*/ })

}