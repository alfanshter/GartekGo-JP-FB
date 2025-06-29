package com.ptpws.GartekGo.Admin.Dialog

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.webkit.MimeTypeMap
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahVidioDialog(
    onDismis: () -> Unit,
    semester: String,
    onSave: (TopikModel) -> Unit,
    topikModel: TopikModel? = null,
) {
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) } // 0f sampai 100f


    var context = LocalContext.current

    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var launchPicker by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedVideoUri = uri
    }

    if (launchPicker) {
        LaunchedEffect(Unit) {
            launchPicker = false
            launcher.launch("video/*")
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


                    Spacer(modifier = Modifier.height(12.dp))

                    // Upload File (dummy)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Upload File Video",
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
                                    launcher.launch("video/*") // hanya tampilkan file video
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            val fileNameToShow = when {
                                selectedVideoUri != null -> getVideoFileNameFromUri(
                                    context,
                                    selectedVideoUri!!
                                )

                                topikModel?.file_video != null -> topikModel.nama_video
                                    ?: "Belum ada Video"

                                else -> "Belum ada Video"
                            }

                            Text(
                                fileNameToShow, modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Delete, contentDescription = null)
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
                            onClick = {

                                    // === EDIT ===
                                    if (selectedVideoUri == null) {
                                        // Tidak pilih file baru, pakai file lama
                                        onDismis()
                                    }
                                    else {
                                        val selectedFileName =
                                            getVideoFileNameFromUri(context, selectedVideoUri!!)
                                        if (selectedFileName == topikModel!!.nama_video) {
                                            // Nama file sama, tidak perlu upload ulang PDF
                                            onDismis()
                                        }
                                        else {
                                            isUploading = true
                                            if (topikModel.path_video!=null){
                                                //delete storage
                                                val storageRef = FirebaseStorage.getInstance().getReference(topikModel.path_video!!)
                                                storageRef.delete()
                                                    .addOnSuccessListener {
                                                        uploadPdfToFirebaseVideo(
                                                            context = context,
                                                            uri = selectedVideoUri!!,
                                                            onSuccess = { url, pathName ->
                                                                savePdfMetadataToFirestoreVideo(
                                                                    idTopik = topikModel.id, // atau input dari UI
                                                                    fileUrl = url,
                                                                    namaFile = getVideoFileNameFromUri(
                                                                        context = context,
                                                                        selectedVideoUri!!
                                                                    ),
                                                                    onSuccess = {
                                                                        //bikin feedback
                                                                        val newTopik = TopikModel(
                                                                            id = topikModel.id,
                                                                            semester = semester,
                                                                            file_video = url,
                                                                            path_video = pathName,
                                                                            nama_video = getVideoFileNameFromUri(
                                                                                context,
                                                                                selectedVideoUri!!
                                                                            )
                                                                        )
                                                                        isUploading = false
                                                                        Log.d("dinda 1", "TambahVidioDialog: $newTopik")

                                                                        onSave(newTopik)

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
                                                                    },
                                                                    pathName = pathName
                                                                )
                                                            },
                                                            onFailure = {
                                                                Toast.makeText(
                                                                    context,
                                                                    "Upload gagal: ${it.message}",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            },
                                                            onProgress = { percent ->
                                                                isUploading = true
                                                                uploadProgress = percent
                                                            }
                                                        )
                                                    }
                                                    .addOnFailureListener {
                                                        Log.e("Firebase", "Gagal hapus file: ${it.message}")
                                                    }
                                            }else{
                                                uploadPdfToFirebaseVideo(
                                                    context = context,
                                                    uri = selectedVideoUri!!,
                                                    onSuccess = { url, pathName ->
                                                        savePdfMetadataToFirestoreVideo(
                                                            idTopik = topikModel.id, // atau input dari UI
                                                            fileUrl = url,
                                                            namaFile = getVideoFileNameFromUri(
                                                                context = context,
                                                                selectedVideoUri!!
                                                            ),
                                                            onSuccess = {
                                                                //bikin feedback
                                                                val newTopik = TopikModel(
                                                                    id = topikModel.id,
                                                                    semester = semester,
                                                                    file_video = url,
                                                                    path_video = pathName,
                                                                    nama_video = getVideoFileNameFromUri(
                                                                        context,
                                                                        selectedVideoUri!!
                                                                    )
                                                                )
                                                                Log.d("dinda 1", "TambahVidioDialog: $newTopik")
                                                                isUploading = false
                                                                onSave(newTopik)

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
                                                            },
                                                            pathName = pathName
                                                        )
                                                    },
                                                    onFailure = {
                                                        Toast.makeText(
                                                            context,
                                                            "Upload gagal: ${it.message}",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    },
                                                    onProgress = { percent ->
                                                        isUploading = true
                                                        uploadProgress = percent
                                                    }
                                                )
                                            }



                                        }
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


fun getVideoFileNameFromUri(context: Context, uri: Uri): String {
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

fun uploadPdfToFirebaseVideo(
    context: Context,
    uri: Uri,
    onSuccess: (downloadUrl: String, path_name: String) -> Unit,
    onFailure: (Exception) -> Unit,
    onProgress: (Float) -> Unit
) {
    val storage = FirebaseStorage.getInstance()

    // Deteksi ekstensi dari mimeType
    val contentResolver = context.contentResolver
    val type = contentResolver.getType(uri)
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type) ?: "mp4"

    val fileName = "${System.currentTimeMillis()}.$extension"
    val storageRef = storage.reference.child("public/$fileName")
    val path_name = "public/$fileName"
    val uploadTask = storageRef.putFile(uri)

    uploadTask
        .addOnProgressListener { taskSnapshot ->
            val progress =
                (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
            onProgress(progress)
        }
        .addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                onSuccess(downloadUrl.toString(), path_name)
            }
        }
        .addOnFailureListener {
            onFailure(it)
        }
}

fun savePdfMetadataToFirestoreVideo(
    idTopik: String,
    fileUrl: String?,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit,
    namaFile: String,
    pathName: String
) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"


    val data = hashMapOf(
        "file_video" to fileUrl,
        "nama_video" to namaFile,
        "path_video" to pathName,
        "uploadedMateriBy" to userId,
        "uploadedMateriAt" to FieldValue.serverTimestamp()
    )

    firestore.collection("topik").document(idTopik).update(data)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { onFailure(it) }
}





@Preview
@Composable
private fun TambahVidioDialogPreview() {
    TambahVidioDialog(
        onDismis = { /*TODO*/ },
        semester = "1",
        onSave = { ""},
        topikModel = TopikModel(),
    )

}