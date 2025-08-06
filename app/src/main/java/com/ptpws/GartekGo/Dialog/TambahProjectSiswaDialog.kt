package com.ptpws.GartekGo.Dialog

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ptpws.GartekGo.Admin.Dialog.compressImage
import com.ptpws.GartekGo.Admin.Dialog.createImageUri
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import com.ptpws.GartekGo.model.TopikModel
import kotlinx.coroutines.launch
import java.io.File

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun TambahProjectSiswaDialog(
    onDismiss: () -> Unit = {},
    topikModel: TopikModel,
    onSave: (TopikModel) -> Unit,
    gambarSoalProject: String?,
    idProject: String,
    kelas: String,
    program_keahlian: String,

    ) {

    println(gambarSoalProject)

    var context = LocalContext.current
    var imageUri = remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) } // 0f sampai 100f
    var selectedIdTopik by remember { mutableStateOf(topikModel?.id ?: "") }
    val initialGambarProject = gambarSoalProject ?: ""
    val initialuploadedGambarProjectAt = topikModel?.uploadedGambarProjectAt
    val initialNamaFileProject = topikModel?.nama_file_project ?: ""


    var gambarProject by remember { mutableStateOf(initialGambarProject) }
    var uploadGambarProjectAt by remember { mutableStateOf(initialuploadedGambarProjectAt) }
    var namaFileProject by remember { mutableStateOf(initialNamaFileProject) }

    val imagePainter = when {
        imageUri.value != null -> {
            rememberAsyncImagePainter(imageUri.value)
        }

        !gambarSoalProject.isNullOrBlank() -> {
            rememberAsyncImagePainter(model = gambarSoalProject)
        }

        else -> {
            painterResource(id = R.drawable.gambartugas)
        }
    }


    val showChooserDialog = remember { mutableStateOf(false) }


    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    // Galeri
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { imageUri.value = it }
    }
    val coroutineScope = rememberCoroutineScope()

    // Kamera
    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            coroutineScope.launch {
                val compressedUri = compressImage(context, cameraImageUri.value!!)
                imageUri.value = compressedUri
                Log.d("dinda", "Size: ${File(imageUri.value?.path!!).length() / 1024} KB")
            }
        }
    }


    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false // untuk bisa custom ukuran
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)), contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                // Card utama
                Card(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            "Upload gambar soal (Opsional)",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff020202),
                            fontSize = 12.sp
                        )
                        Image(
                            painter = imagePainter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(300.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    showChooserDialog.value = true
                                },
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Button(
                                onClick = {
                                    // Simpan data di sini jika perlu
                                    // === EDIT ===
                                    if (imageUri.value == null) {
                                        uploadImageToFirebase(
                                            context = context,
                                            uri = imageUri.value!!,
                                            onSuccess = { url, pathName ->
                                                gambarProject = url
                                                namaFileProject = pathName

                                                saveGambarProjectToFirestore(
                                                    idTopik = selectedIdTopik, // atau input dari UI
                                                    gambarProject = gambarProject,
                                                    onSuccess = {
                                                        val updatedTopik = topikModel!!.copy(
                                                            gambar_project = gambarProject,
                                                            uploadedGambarProjectAt = uploadGambarProjectAt
                                                        )

                                                        isUploading = false
                                                        onSave(updatedTopik)
                                                        Toast.makeText(
                                                            context,
                                                            "Upload dan simpan ke Firestore berhasil!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        onDismiss()
                                                    },
                                                    onFailure = {
                                                        isUploading = false
                                                        Toast.makeText(
                                                            context,
                                                            "Gagal simpan Firestore: ${it.message}",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    },
                                                    namaFileProject = namaFileProject,
                                                    idProject,
                                                    kelas,program_keahlian
                                                )
                                            },
                                            onProgress = { percent ->
                                                isUploading = true
                                                uploadProgress = percent
                                            },
                                            onFailure = {
                                                Toast.makeText(
                                                    context,
                                                    "Upload gagal: ${it.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )

                                    } else {
                                        //jika kita sudah ada ya

                                        uploadImageToFirebase(
                                            context = context,
                                            uri = imageUri.value!!,
                                            onSuccess = { url, pathName ->
                                                gambarProject = url
                                                namaFileProject = pathName

                                                saveGambarProjectToFirestore(
                                                    idTopik = selectedIdTopik, // atau input dari UI
                                                    gambarProject = gambarProject,
                                                    onSuccess = {
                                                        val updatedTopik = topikModel!!.copy(
                                                            gambar_project = gambarProject,
                                                            uploadedGambarProjectAt = uploadGambarProjectAt
                                                        )

                                                        isUploading = false
                                                        onSave(updatedTopik)
                                                        Toast.makeText(
                                                            context,
                                                            "Upload dan simpan ke Firestore berhasil!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                        onDismiss()
                                                    },
                                                    onFailure = {
                                                        isUploading = false
                                                        Toast.makeText(
                                                            context,
                                                            "Gagal simpan Firestore: ${it.message}",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    },
                                                    namaFileProject = namaFileProject,
                                                    idProject = idProject,
                                                    kelas = kelas,
                                                    program_keahlian = program_keahlian
                                                )
                                            },
                                            onProgress = { percent ->
                                                isUploading = true
                                                uploadProgress = percent
                                            },
                                            onFailure = {
                                                Toast.makeText(
                                                    context,
                                                    "Upload gagal: ${it.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )

                                    }

                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFF00C853
                                    )
                                ),
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.height(60.dp)
                            ) {
                                Text(
                                    "Kumpulkan",
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
                        IconButton(onClick = { onDismiss() }) {
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

    // Dialog Pilihan Kamera / Galeri
    if (showChooserDialog.value) {
        AlertDialog(
            onDismissRequest = { showChooserDialog.value = false },
            title = { Text("Pilih Gambar Dari") },
            text = {
                Column {
                    Text(
                        "Galeri",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showChooserDialog.value = false
                                galleryLauncher.launch("image/*")
                            }
                            .padding(8.dp)
                    )
                    Text(
                        "Kamera",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (cameraPermissionState.status.isGranted) {
                                    // ✅ Izin kamera sudah diberikan → langsung ambil foto
                                    showChooserDialog.value = false
                                    val uri = createImageUri(context = context)
                                    cameraImageUri.value = uri
                                    uri?.let { cameraLauncher.launch(it) }
                                } else if (cameraPermissionState.status.shouldShowRationale) {
                                    // ❌ Pernah ditolak sebelumnya → tampilkan alasan
                                    Toast.makeText(
                                        context,
                                        "Aplikasi membutuhkan izin kamera untuk mengambil gambar.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    cameraPermissionState.launchPermissionRequest()
                                } else {
                                    // ❌❌ Pertama kali atau ditolak permanen
                                    cameraPermissionState.launchPermissionRequest()
                                }
                            }
                            .padding(8.dp)
                    )
                }
            },
            confirmButton = {}
        )
    }

}


fun uploadImageToFirebase(
    context: Context,
    uri: Uri,
    onSuccess: (downloadUrl: String, path_name: String) -> Unit,
    onFailure: (Exception) -> Unit,
    onProgress: (Float) -> Unit
) {
    val storage = FirebaseStorage.getInstance()

    // Ekstensi file dari URI
    val contentResolver = context.contentResolver
    val type = contentResolver.getType(uri) ?: "image/jpeg"
    val extension = when (type) {
        "image/png" -> ".png"
        "image/webp" -> ".webp"
        else -> ".jpg" // default
    }

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


fun saveGambarProjectToFirestore(
    idTopik: String,
    gambarProject: String?,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit,
    namaFileProject: String,
    idProject: String,
    kelas: String,
    program_keahlian: String
) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"


    val data = hashMapOf(
        "imageUrl" to gambarProject,
        "kelas" to kelas,
        "program_keahlian" to program_keahlian,
        "timestamp" to FieldValue.serverTimestamp()
    )

    firestore.collection("project_uploads").document(idProject).update(data)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { onFailure(it) }
}

@Preview(showBackground = true)
@Composable
private fun NamaTopikDialogPreview() {
    TambahProjectSiswaDialog(
        onDismiss = {},
        topikModel = TopikModel(),
        onSave = {},
        gambarSoalProject = "",
        idProject = "idProject",
        kelas = "kelas",
        program_keahlian = "program_keahlian"
    )

}