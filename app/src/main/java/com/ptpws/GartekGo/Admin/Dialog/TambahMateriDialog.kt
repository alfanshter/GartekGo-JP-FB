package com.ptpws.GartekGo.Admin.Dialog

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ptpws.GartekGo.Admin.model.TopikModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahMateriDialog(
    onDismis: () -> Unit,
    semester: String,
    topikList: SnapshotStateList<TopikModel>
) {
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) } // 0f sampai 100f

    var materiText by remember { mutableStateOf("") }
    var selectedTopik by remember { mutableStateOf("") }
    var selectedIdTopik by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var context = LocalContext.current

    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }
    var launchPicker by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedPdfUri = uri
    }

    if (launchPicker) {
        LaunchedEffect(Unit) {
            launchPicker = false
            launcher.launch("application/pdf")
        }
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
                        value = materiText,
                        onValueChange = { materiText = it },
                        placeholder = {
                            Text(
                                "Nama Materi",
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

                    // Upload File (dummy)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Upload File PDF",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff020202),
                            fontSize = 12.sp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(12.dp))
                                .padding(12.dp)
                                .clickable {
                                    launcher.launch("application/pdf")
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                if (selectedPdfUri != null) getFileNameFromUri(
                                    context,
                                    selectedPdfUri!!
                                ) else "Belum ada PDF", modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Delete, contentDescription = null)
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
                                val sudahAdaMateri = !topik.nama_materi.isNullOrBlank()
                                Log.d("dinda","${topik}")
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            if (sudahAdaMateri) "${topik.nama} - Sudah ada Materi" else topik.nama,
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    },
                                    onClick = {
                                        if (!sudahAdaMateri) {
                                            selectedTopik = topik.nama
                                            selectedIdTopik = topik.id
                                            expanded = false
                                        }else{
                                            Toast.makeText(
                                                context,
                                                "Topik sudah ada materi",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
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
                            Text(
                                "HAPUS", fontFamily = poppinsfamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp, color = Color.White
                            )
                        }

                        Button(
                            onClick = {
                                selectedPdfUri?.let { uri ->
                                    isUploading = true
                                    uploadPdfToFirebase(
                                        context = context,
                                        uri = uri,
                                        onSuccess = { url ->
                                            savePdfMetadataToFirestore(
                                                nama = materiText,
                                                topik = selectedTopik, // atau input dari UI
                                                idTopik = selectedIdTopik, // atau input dari UI
                                                fileUrl = url,
                                                onSuccess = {
                                                    isUploading = false
                                                    Toast.makeText(
                                                        context,
                                                        "Upload dan simpan ke Firestore berhasil!",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    onDismis()
                                                },
                                                onFailure = {
                                                    isUploading = false
                                                    Toast.makeText(
                                                        context,
                                                        "Gagal simpan Firestore: ${it.message}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            )
                                        },
                                        onFailure = {
                                            isUploading = false
                                            Toast.makeText(
                                                context,
                                                "Upload gagal: ${it.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }, onProgress = { percent ->
                                            uploadProgress = percent
                                        }
                                    )
                                }

                                if (selectedPdfUri == null) {
                                    Toast.makeText(
                                        context,
                                        "Silahkan tambahkan file pdf",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
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

    if (isUploading) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Mengunggah File...") },
            text = {
                Column {
                    LinearProgressIndicator(progress = uploadProgress / 100f)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${uploadProgress.toInt()}%", textAlign = TextAlign.Center)
                }
            },
            confirmButton = {}
        )
    }
}

fun getFileNameFromUri(context: Context, uri: Uri): String {
    var name = "Unknown"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst() && nameIndex != -1) {
            name = it.getString(nameIndex)
        }
    }
    return name
}

fun uploadPdfToFirebase(
    context: Context,
    uri: Uri,
    onSuccess: (downloadUrl: String) -> Unit,
    onFailure: (Exception) -> Unit,
    onProgress: (Float) -> Unit
) {
    val storage = FirebaseStorage.getInstance()

    val fileName = System.currentTimeMillis().toString() + ".pdf"
    val storageRef = FirebaseStorage.getInstance().reference
        .child("public/$fileName")


    val uploadTask = storageRef.putFile(uri)

    uploadTask
        .addOnProgressListener { taskSnapshot ->
            val progress =
                (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
            onProgress(progress)
        }
        .addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                onSuccess(downloadUrl.toString())
            }
        }
        .addOnFailureListener {
            onFailure(it)
        }
}

fun savePdfMetadataToFirestore(
    nama: String,
    topik: String,
    idTopik: String,
    fileUrl: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"

    val data = hashMapOf(
        "nama_materi" to nama,
        "file_materi" to fileUrl,
        "uploadedMateriBy" to userId,
        "uploadedMateriAt" to FieldValue.serverTimestamp()
    )

    firestore.collection("topik").document(idTopik).update(data)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { onFailure(it) }
}


@Preview(showBackground = true)
@Composable
private fun TambahMateriDialogPreview() {
    TambahMateriDialog(
        onDismis = { /*TODO*/ },
        semester = "1",
        topikList = remember { mutableStateListOf() })

}