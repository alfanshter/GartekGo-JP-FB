package com.ptpws.GartekGo.Admin.Dialog

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.model.TopikModel
import com.ptpws.GartekGo.Commond.poppinsfamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamaTopikDialog(
    onDismis: () -> Unit,
    onSave: (TopikModel) -> Unit,
    onDelete: (String) -> Unit,
    semester: Int,
    idTopik: String,
    updateNama: String,
    topikList: SnapshotStateList<TopikModel>,
    nomor: Int
) {


    var topicText by remember { mutableStateOf("") }
    var nomorBaru by remember { mutableStateOf(nomor.toString()) }

    if (nomor == 0) {
        nomorBaru = ""
    }

    var context = LocalContext.current
    // Set initial value hanya sekali saat composable pertama kali ditampilkan
    LaunchedEffect(key1 = idTopik) {
        topicText = updateNama
    }
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
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    // TextField Input
                    TextField(
                        value = topicText,
                        onValueChange = { topicText = it },
                        placeholder = {
                            Text(
                                "Nama Topik",
                                fontFamily = poppinsfamily,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Gray
                            )
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
                    //Nomor
                    TextField(
                        value = nomorBaru!!,
                        onValueChange = { newText ->
                            if (newText.all { it.isDigit() }) {
                                nomorBaru = newText
                            }
                        },
                        placeholder = {
                            Text(
                                "Masukan nomor topik",
                                fontFamily = poppinsfamily,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Gray
                            )
                        },
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // ✅ FIXED
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val db = Firebase.firestore
                                db.collection("topik").document(idTopik).delete()
                                    .addOnSuccessListener {
                                        Log.d("Firestore", "Topik berhasil dihapus")
                                        onDelete(idTopik)
                                        onDismis()
                                    }.addOnFailureListener {
                                        Log.e("Firestore", "Gagal menghapus topik: ${it.message}")
                                    }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.width(120.dp)
                        ) {
                            Text(
                                "HAPUS",
                                fontFamily = poppinsfamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }

                        Button(
                            onClick = {
                                val db = Firebase.firestore
                                val semesterLabel =
                                    if (semester == 1) 1 else 2

                                //update data
                                //jika id topik tidak null/kosong
                                if (idTopik != "") {
                                    val isUpdate = idTopik.isNotBlank()

                                    val topikSekarang = topikList.find { it.id == idTopik }

                                    val nomorInput = nomorBaru?.toIntOrNull()
                                    val namaInput = topicText.trim()

                                    val namaBerubah = topikSekarang?.nama?.equals(
                                        namaInput,
                                        ignoreCase = true
                                    ) == false
                                    val nomorBerubah = topikSekarang?.nomor != nomorInput


                                    if (namaBerubah) {
                                        val namaSudahAda = topikList.any {
                                            it.nama.equals(
                                                namaInput,
                                                ignoreCase = true
                                            ) && it.id != idTopik
                                        }
                                        if (namaSudahAda) {
                                            Toast.makeText(
                                                context,
                                                "Nama topik sudah digunakan oleh topik lain",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button
                                        }
                                        if (nomorBaru == "0") {
                                            Toast.makeText(
                                                context,
                                                "tidak boleh 0",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button

                                        }
                                    }

                                    if (nomorBerubah) {
                                        val nomorSudahAda = topikList.any {
                                            it.nomor == nomorInput && it.id != idTopik
                                        }
                                        if (nomorSudahAda) {
                                            Toast.makeText(
                                                context,
                                                "Nomor topik sudah digunakan oleh topik lain",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button
                                        }
                                        if (nomorBaru == "0") {
                                            Toast.makeText(
                                                context,
                                                "tidak boleh 0",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@Button

                                        }
                                    }


                                    val updateData = db.collection("topik").document(idTopik)
                                    //object untuk kirim ke db
                                    val dataTopik = mapOf(
                                        "nama" to topicText,
                                        "semester" to semesterLabel,
                                        "nomor" to nomorInput!!.toInt()
                                    )


                                    //update data ke firebase
                                    updateData.update(dataTopik)
                                        .addOnSuccessListener {
                                            //bikin feedback model, untuk ditampung ke Array data
                                            val newTopik = TopikModel(
                                                id = idTopik,
                                                nama = topicText,
                                                semester = semesterLabel,
                                                nomor = nomorInput!!.toInt()
                                            )

                                            onSave(newTopik) // ← kirim model lengkap
                                            onDismis()
                                        }
                                        .addOnFailureListener {
                                            Log.e(
                                                "Firestore",
                                                "Gagal menambahkan topik: ${it.message}"
                                            )
                                        }
                                }
                                //tambah data
                                else {
                                    val nomorSudahAda = topikList.any {
                                        it.nomor == nomorBaru.toInt()
                                    }

                                    val namaSudahAda = topikList.any {
                                        it.nama == topicText
                                    }

                                    if (nomorSudahAda) {
                                        Toast.makeText(
                                            context,
                                            "Nomor sudah ada",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@Button
                                    }

                                    if (namaSudahAda) {
                                        Toast.makeText(
                                            context,
                                            "Nama sudah ada",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@Button
                                    }

                                    if (nomorBaru == "0") {
                                        Toast.makeText(context, "tidak boleh 0", Toast.LENGTH_SHORT)
                                            .show()
                                        return@Button

                                    }
                                    val sendData = db.collection("topik").document()
                                    //object untuk kirim ke db
                                    val dataTopik = hashMapOf(
                                        "nama" to topicText,
                                        "semester" to semesterLabel,
                                        "id" to sendData.id,
                                        "nomor" to nomorBaru.toInt()
                                    )

                                    sendData.set(dataTopik)
                                        .addOnSuccessListener {
                                            //bikin feedback model, untuk ditampung ke Array data
                                            val newTopik = TopikModel(
                                                id = sendData.id,
                                                nama = topicText,
                                                semester = semesterLabel,
                                                nomor = nomorBaru.toInt()
                                            )

                                            onSave(newTopik) // ← kirim model lengkap
                                            onDismis()
                                        }
                                        .addOnFailureListener {
                                            Log.e(
                                                "Firestore",
                                                "Gagal menambahkan topik: ${it.message}"
                                            )
                                        }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.width(120.dp)
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

            // Tombol close bulat
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = (-29).dp)
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
}




@Preview(showBackground = true)
@Composable
private fun NamaTopikDialogPreview() {
//    NamaTopikDialog (onDismis = { /*TODO*/ }, onSave = { }, semester = "")

}
