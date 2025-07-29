package com.ptpws.GartekGo.Admin.Dialog

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.firebase.firestore.FirebaseFirestore
import com.ptpws.GartekGo.model.SoalModel
import com.ptpws.GartekGo.model.TopikModel
import com.ptpws.GartekGo.Commond.poppinsfamily

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahSoalDialog(
    onDismis: () -> Unit,
    onSave: (SoalModel) -> Unit,
    topikModel: TopikModel,
    soalModel: SoalModel?,
    onDelete: (String) -> Unit
) {
    // Inisialisasi state hanya sekali (saat pertama kali komposisi)
    val initialSoal = soalModel?.soal ?: ""
    val initialJawabanMap = soalModel?.jawaban ?: emptyMap()
    val initialJawabanList = initialJawabanMap.values.toList().ifEmpty { listOf("") }
    val initialJawabanBenar = soalModel?.jawaban_benar ?: ""

    var ketiksoal by remember { mutableStateOf(initialSoal) }
    val pilihanJawaban = remember { mutableStateListOf(*initialJawabanList.toTypedArray()) }
    var jawabanBenar by remember { mutableStateOf(initialJawabanBenar) }

    val context = LocalContext.current
    var isSaving by remember { mutableStateOf(false) }
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
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .padding(top = 20.dp),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF))
            ) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    item {
                        Column() {
                            // Nama Materi
                            Spacer(modifier = Modifier.height(12.dp))

                            // Upload File ()
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    "Masukkan Soal",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff020202),
                                    fontSize = 12.sp
                                )

                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TextField(
                                        value = ketiksoal,
                                        onValueChange = { ketiksoal = it },
                                        placeholder = {
                                            Text(
                                                "Ketik Soal",
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
                                            .padding(vertical = 4.dp),
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.White,
                                            unfocusedContainerColor = Color.White,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        )
                                    )

                                    // Icon hapus
                                    if (ketiksoal.isNotEmpty()) {
                                        IconButton(
                                            onClick = {
                                                ketiksoal = ""
                                            },
                                            modifier = Modifier
                                                .align(Alignment.CenterEnd)
                                                .padding(end = 8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Hapus teks",
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                }

                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            //Jawaban
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    "Masukkan Jawaban",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff020202),
                                    fontSize = 12.sp
                                )

                                pilihanJawaban.forEachIndexed { index, jawaban ->
                                    TextField(
                                        value = jawaban,
                                        onValueChange = {
                                            pilihanJawaban[index] = it
                                            // Jika field terakhir sedang diketik, tambahkan field kosong baru di bawahnya
                                            if (index == pilihanJawaban.lastIndex && it.isNotBlank()) {
                                                pilihanJawaban.add("")
                                            }


                                        },
                                        label = {
                                            Text(
                                                "Jawaban ${('A' + index)}",
                                                fontFamily = poppinsfamily,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 12.sp
                                            )
                                        },
                                        placeholder = {
                                            Text(
                                                "Ketik Soal",
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
                                            .padding(vertical = 4.dp), // memberi ruang untuk ikon hapus
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.White,
                                            unfocusedContainerColor = Color.White,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(12.dp))


                                Text(
                                    "Masukkan Jawaban Benar",
                                    fontFamily = poppinsfamily,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff020202),
                                    fontSize = 12.sp
                                )
                                TextField(
                                    value = jawabanBenar,
                                    onValueChange = { jawabanBenar = it.lowercase() },
                                    placeholder = {
                                        Text(
                                            "(contoh: a, b, c)",
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
                                        .padding(vertical = 4.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )

                                Spacer(modifier = Modifier.height(12.dp))


                            }


                            // Pilih Topik

                            Spacer(modifier = Modifier.height(20.dp))

                            // Tombol aksi
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(
                                    onClick = {
                                        showConfirmDialog = true
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.width(120.dp)
                                ) {
                                    Text(
                                        "HAPUS", fontFamily = poppinsfamily,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp, color = Color.White
                                    )
                                }

                                Button(
                                    onClick = {
                                        val jawabanMap: Map<String, String> = pilihanJawaban
                                            .filter { it.isNotBlank() }
                                            .withIndex()
                                            .associate { (index, value) ->
                                                ('a' + index).toString() to value
                                            }

                                        val topik = FirebaseFirestore.getInstance()
                                            .collection("topik")
                                            .document(topikModel.id)

                                        saveSoal(
                                            context = context,
                                            topikModel = topikModel,
                                            soalModel = SoalModel(
                                                soal = ketiksoal,
                                                jawaban = jawabanMap,
                                                jawaban_benar = jawabanBenar,
                                                topik = topik,
                                                id_soal = soalModel!!.id_soal
                                            ),
                                            onSuccess = {
                                                // Contoh: reset form
                                                onSave(it)
                                                onDismis()
                                                ketiksoal = ""
                                                pilihanJawaban.clear()
                                                pilihanJawaban.add("")
                                                jawabanBenar = ""
                                                Toast.makeText(
                                                    context,
                                                    "Soal disimpan",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            },
                                            onFailure = {
                                                Log.e("SaveSoal", "Gagal simpan: ${it.message}")
                                                Toast.makeText(
                                                    context,
                                                    "Silahkan coba lagi",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            },
                                            setSaving = {
                                                isSaving = it
                                            }
                                        )
                                    },
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

    if (isSaving) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Menyimpan Soal...") },
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

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    deleteSoal(
                        context = context,
                        soal = soalModel!!,
                        onSuccess = {
                            onDismis()
                            onDelete(soalModel.id_soal)
                            ketiksoal = ""
                            pilihanJawaban.clear()
                            pilihanJawaban.add("")
                            jawabanBenar = ""
                        },
                        onFailure = {
                            Log.e("hapus", "Gagal simpan: ${it.message}")
                            Toast.makeText(
                                context,
                                "Silahkan coba lagi",
                                Toast.LENGTH_SHORT
                            ).show()
                        })

                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Batal")
                }
            },
            title = { Text("Hapus Soal") },
            text = { Text("Yakin ingin menghapus soal ini?") }
        )
    }

}


fun saveSoal(
    context: Context,
    topikModel: TopikModel,
    soalModel: SoalModel,
    onSuccess: (SoalModel) -> Unit,
    onFailure: (Exception) -> Unit,
    setSaving: (Boolean) -> Unit,
) {
    val isEdit = soalModel.id_soal.isNotBlank()
    val soalRef = if (isEdit) {
        FirebaseFirestore.getInstance().collection("soal").document(soalModel.id_soal)
    } else {
        FirebaseFirestore.getInstance().collection("soal").document() // Auto-ID
    }

    val soalData = hashMapOf(
        "id_soal" to soalRef.id,
        "soal" to soalModel.soal,
        "jawaban" to soalModel.jawaban,
        "jawaban_benar" to soalModel.jawaban_benar,
        "topik" to soalModel.topik
    )

    setSaving(true)

    soalRef.set(soalData)
        .addOnSuccessListener {
            val createdSoal = SoalModel(
                soal = soalModel.soal,
                id_soal = soalRef.id,
                jawaban = soalModel.jawaban,
                jawaban_benar = soalModel.jawaban_benar,
                topik = soalModel.topik// atau topikModel.id
            )

            setSaving(false)
            Toast.makeText(
                context,
                if (isEdit) "Soal berhasil diupdate" else "Soal berhasil ditambahkan",
                Toast.LENGTH_SHORT
            ).show()
            onSuccess(createdSoal)
        }
        .addOnFailureListener { e ->
            setSaving(false)
            Toast.makeText(context, "Gagal tambah soal: ${e.message}", Toast.LENGTH_SHORT).show()
            onFailure(e)
        }


}

fun deleteSoal(
    context: Context,
    soal: SoalModel,
    onSuccess: (SoalModel) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    val soalRef = firestore.collection("soal").document(soal.id_soal)

    soalRef.delete()
        .addOnSuccessListener {
            Toast.makeText(context, "Soal berhasil dihapus", Toast.LENGTH_SHORT).show()
            onSuccess(soal)
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Gagal menghapus soal: ${e.message}", Toast.LENGTH_SHORT).show()
            onFailure(e)
        }
}


@Preview(showBackground = true)
@Composable
private fun TambahSoalDialogPreview() {
    TambahSoalDialog(
        onDismis = { /*TODO*/ },
        onSave = { "" },
        topikModel = TopikModel(),
        soalModel = SoalModel(),
        onDelete = { idSoal ->

        }
    )

}