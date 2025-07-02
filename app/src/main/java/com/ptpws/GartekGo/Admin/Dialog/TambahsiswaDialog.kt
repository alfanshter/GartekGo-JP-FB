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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.ptpws.GartekGo.Admin.model.RegisterUserRequest
import com.ptpws.GartekGo.Admin.model.UpdateUserRequest
import com.ptpws.GartekGo.Admin.model.UsersModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import com.ptpws.GartekGo.network.ApiClient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahsiswaDialog(
    onDismis: () -> Unit,
    onSave: (UsersModel) -> Unit,
    usersModel: UsersModel?,
    onDelete: (String) -> Unit,
) {
    val initialNama = usersModel?.nama ?: ""
    val initialEmail = usersModel?.email ?: ""
    val initialNomorAbsen = usersModel?.nomor_absen.toString() ?: ""
    val initialSelectedKelas = usersModel?.kelas ?: ""
    val initialSelectedKeahlian = usersModel?.program_keahlian ?: ""

    var nama by remember { mutableStateOf(initialNama) }
    var email by remember { mutableStateOf(initialEmail) }
    var absen by remember { mutableStateOf(initialNomorAbsen) }
    var selectedkelas by remember { mutableStateOf(initialSelectedKelas) }
    var selectedkeahlian by remember { mutableStateOf(initialSelectedKeahlian) }

    val kelasList = listOf("10", "11", "12")
    var expanded by remember { mutableStateOf(false) }
    var expandedkeahlian by remember { mutableStateOf(false) }
    val keahlianList = listOf("TKJ", "TKRO", "DKV")

    val scope = rememberCoroutineScope()
    var message by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }


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

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Nama",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(Modifier.height(4.dp))

                    TextField(
                        value = nama,
                        onValueChange = { nama = it },
                        placeholder = {
                            (Text(
                                "Masukkan Nama",
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
                            "Email",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(Modifier.height(4.dp))

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = {
                            (Text(
                                "E-mail",
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
                    Spacer(Modifier.height(23.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "No Absen",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(Modifier.height(4.dp))

                    TextField(
                        value = absen,
                        onValueChange = { newText -> // Hanya izinkan angka
                            absen = newText.filter { it.isDigit() }
                        },
                        placeholder = {
                            (Text(
                                "Nomer Absen",
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, // Keyboard angka
                            imeAction = ImeAction.Done
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
                    Spacer(Modifier.height(16.dp))

                    // Pilih Topik
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = selectedkelas,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = {
                                Text(
                                    "Pilih Kelas",
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
                            kelasList.forEach { Kelas ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            Kelas,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    },
                                    onClick = {
                                        selectedkelas = Kelas
                                        expanded = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Pilih Topik
                    ExposedDropdownMenuBox(
                        expanded = expandedkeahlian,
                        onExpandedChange = { expandedkeahlian = !expandedkeahlian },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = selectedkeahlian,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = {
                                Text(
                                    "Pilih Program Keahlian",
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
                            expanded = expandedkeahlian,
                            onDismissRequest = { expandedkeahlian = false },
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                        ) {
                            keahlianList.forEach { Keahlian ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            Keahlian,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    },
                                    onClick = {
                                        selectedkeahlian = Keahlian
                                        expanded = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { showConfirmDialog  = true},
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .width(139.dp)
                                .height(60.dp)
                        ) {
                            Text(
                                "HAPUS", fontFamily = poppinsfamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp, color = Color.White
                            )
                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    isLoading = true
                                    try {
                                        if (usersModel != null) {
                                            val request = UpdateUserRequest(
                                                email = email,
                                                nama = nama,
                                                kelas = selectedkelas,
                                                nomorAbsen = absen,
                                                programKeahlian = selectedkeahlian,
                                                uid = usersModel.uid!!
                                            )

                                            val response =
                                                ApiClient.getService(ApiClient.updateService)
                                                    .updateUser(request)

                                            if (response.isSuccessful) {
                                                when (response.code()) {
                                                    200, 201 -> {
                                                        onDismis()
                                                        isLoading = false
                                                        onSave(
                                                            UsersModel(
                                                                uid = usersModel.uid,
                                                                email = email,
                                                                nama = nama,
                                                                kelas = selectedkelas,
                                                                nomor_absen = absen.toInt(),
                                                                program_keahlian = selectedkeahlian
                                                            )
                                                        )
                                                        message =
                                                            "✅ Berhasil update: ${response.body()?.message}"
                                                    }

                                                    500 -> {
                                                        isLoading = false
                                                        message = "Email sudah digunakan"
                                                        showDialog = true
                                                    }

                                                    else -> {
                                                        isLoading = false
                                                        showDialog = true
                                                        message = "${response.message()}"
                                                    }
                                                }
                                            } else {
                                                when (response.code()) {
                                                    500 -> {
                                                        isLoading = false
                                                        showDialog = true
                                                        message = "Email sudah digunakan"
                                                    }

                                                    else -> {
                                                        isLoading = false
                                                        showDialog = true
                                                        message = "${response.message()}"
                                                    }

                                                }

                                            }
                                        } else {
                                            val request = RegisterUserRequest(
                                                email = email,
                                                nama = nama,
                                                kelas = selectedkelas,
                                                nomorAbsen = absen,
                                                programKeahlian = selectedkeahlian
                                            )

                                            val response =
                                                ApiClient.getService(ApiClient.registerService)
                                                    .registerUser(request)

                                            if (response.isSuccessful) {
                                                when (response.code()) {
                                                    200, 201 -> {
                                                        onDismis()
                                                        isLoading = false
                                                        onSave(
                                                            UsersModel(
                                                                email = email,
                                                                nama = nama,
                                                                kelas = selectedkelas,
                                                                nomor_absen = absen.toInt(),
                                                                program_keahlian = selectedkeahlian
                                                            )
                                                        )
                                                        message =
                                                            "✅ Berhasil daftar: ${response.body()?.message}"
                                                    }

                                                    500 -> {
                                                        isLoading = false
                                                        message = "Email sudah digunakan"
                                                        showDialog = true
                                                    }

                                                    else -> {
                                                        isLoading = false
                                                        showDialog = true
                                                        message = "${response.message()}"
                                                    }
                                                }
                                            } else {
                                                when (response.code()) {
                                                    500 -> {
                                                        isLoading = false
                                                        showDialog = true
                                                        message = "Email sudah digunakan"
                                                    }

                                                    else -> {
                                                        isLoading = false
                                                        showDialog = true
                                                        message = "${response.message()}"
                                                    }

                                                }

                                            }
                                        }


                                    } catch (e: Exception) {
                                        message = "❌ Error: ${e.message}"
                                    } finally {
                                        isLoading = false
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(
                                    0xFF00C853
                                )
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .width(139.dp)
                                .height(60.dp)
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

            if (isLoading) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text("Data Siswa") },
                    text = {
                        Column {
                            CircularProgressIndicator()
                            Spacer(Modifier.height(8.dp))
                            Text("Silakan tunggu...")
                        }
                    },
                    confirmButton = {}
                )
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
                    IconButton(onClick = { onDismis() }) {
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = {
                Text("Informasi")
            },
            text = {
                Text(message!!)
            }
        )
    }


    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    isLoading = true
                    scope.launch {
                        showConfirmDialog = false
                        val request = UpdateUserRequest(
                            email = email,
                            nama = nama,
                            kelas = selectedkelas,
                            nomorAbsen = absen,
                            programKeahlian = selectedkeahlian,
                            uid = usersModel!!.uid!!
                        )

                        val response =
                            ApiClient.getService(ApiClient.deleteService).deleteUser(request)

                        if (response.isSuccessful) {
                            when (response.code()) {
                                200, 201 -> {
                                    onDismis()
                                    isLoading = false
                                    onDelete(usersModel.uid!!)
                                    message = "✅ Berhasil delete: ${response.body()?.message}"
                                }

                                else -> {
                                    isLoading = false
                                    showDialog = true
                                    message = "${response.message()}"
                                }
                            }
                        } else {
                            isLoading = false
                            showDialog = true
                            message = "Hapus gagal, silahkan coba lagi"

                        }
                    }


                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Batal")
                }
            },
            title = { Text("Hapus Siswa") },
            text = { Text("Yakin ingin menghapus siswa ini?") }
        )
    }


}


@Preview
@Composable
private fun TambahsiswaDialogPreview() {
    TambahsiswaDialog(
        onDismis = { /*TODO*/ },
        onSave = { "" },
        usersModel = UsersModel(),
        onDelete = {}
    )

}