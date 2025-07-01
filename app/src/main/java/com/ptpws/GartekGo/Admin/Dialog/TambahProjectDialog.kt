package com.ptpws.GartekGo.Admin.Dialog

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ptpws.GartekGo.Admin.model.TopikModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun TambahProjectDialog(
    onDismis: () -> Unit,
    semester: String,
    onSave: (TopikModel) -> Unit,
    topikModel: TopikModel? = null,
) {
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) } // 0f sampai 100f
    val initialSoal = topikModel?.soal_project ?: ""
    val initialGambarProject = topikModel?.gambar_project ?: ""
    val initialNamaFileProject = topikModel?.nama_file_project ?: ""
    val initialuploadedGambarProjectAt = topikModel?.uploadedGambarProjectAt

    //input
    var soalProject by remember { mutableStateOf(initialSoal) }
    var gambarProject by remember { mutableStateOf(initialGambarProject) }
    var namaFileProject by remember { mutableStateOf(initialNamaFileProject) }
    var uploadGambarProjectAt by remember { mutableStateOf(initialuploadedGambarProjectAt) }


    var selectedTopik by remember { mutableStateOf(topikModel?.nama ?: "") }
    var selectedIdTopik by remember { mutableStateOf(topikModel?.id ?: "") }
    var context = LocalContext.current

    var imageUri = remember { mutableStateOf<Uri?>(null) }

    val imagePainter = when {
        imageUri.value != null -> {
            rememberAsyncImagePainter(imageUri.value)
        }
        !topikModel!!.gambar_project.isNullOrBlank() -> {
            rememberAsyncImagePainter(model = topikModel.gambar_project)
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
                            "Tambahkan soal",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xff020202),
                            fontSize = 12.sp
                        )
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = soalProject,
                                onValueChange = { soalProject = it },
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
                            if (soalProject.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        soalProject = ""
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
                                    if (imageUri.value == null) {
                                        saveGambarProjectToFirestore(
                                            idTopik = selectedIdTopik, // atau input dari UI
                                            gambarProject = gambarProject,
                                            soalProject = soalProject,
                                            onSuccess = {
                                                val updatedTopik = topikModel!!.copy(
                                                    gambar_project = gambarProject,
                                                    soal_project = soalProject,
                                                    uploadedGambarProjectAt = uploadGambarProjectAt
                                                )

                                                isUploading = false
                                                onSave(updatedTopik)
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
                                            namaFileProject = namaFileProject
                                        )

                                    }
                                    else {
                                        //jika kita sudah ada ya

                                        uploadImageToFirebase(
                                            context = context,
                                            uri = imageUri.value!!,
                                            onSuccess = {url, pathName->
                                                gambarProject = url
                                                namaFileProject = pathName

                                                saveGambarProjectToFirestore(
                                                    idTopik = selectedIdTopik, // atau input dari UI
                                                    gambarProject = gambarProject,
                                                    soalProject = soalProject,
                                                    onSuccess = {
                                                        val updatedTopik = topikModel!!.copy(
                                                            gambar_project = gambarProject,
                                                            soal_project = soalProject,
                                                            uploadedGambarProjectAt = uploadGambarProjectAt
                                                        )

                                                        isUploading = false
                                                        onSave(updatedTopik)
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
                                                    namaFileProject = namaFileProject
                                                )
                                            },
                                            onProgress = {percent ->
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
    onSuccess: (downloadUrl: String, path_name : String) -> Unit,
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
                onSuccess(downloadUrl.toString(),path_name)
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
    soalProject: String,
    namaFileProject: String
) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"


    val data = hashMapOf(
        "gambar_project" to gambarProject,
        "nama_file_project" to namaFileProject,
        "soal_project" to soalProject,
        "uploadedGambarProjectAt" to FieldValue.serverTimestamp()
    )

    firestore.collection("topik").document(idTopik).update(data)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { onFailure(it) }
}


// Buat Uri untuk kamera
fun createImageUri(context: Context): Uri? {
    val contentResolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "camera_image_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

suspend fun compressImage(context: Context, uri: Uri): Uri? {
    val bitmap = withContext(Dispatchers.IO) {
        val input = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(input)
    } ?: return null

    val compressedFile = withContext(Dispatchers.IO) {
        File.createTempFile("compressed_", ".jpg", context.cacheDir)
    }

    withContext(Dispatchers.IO) {
        val out = FileOutputStream(compressedFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out) // 70 = kualitas (bisa sesuaikan)
        out.flush()
        out.close()
    }

    return compressedFile.toUri()
}


@Preview(showBackground = true)
@Composable
private fun TambahProjectDialogPreview() {
    TambahProjectDialog(
        onDismis = { /*TODO*/ },
        semester = "1",
        onSave = { ""},
        topikModel = TopikModel(),
    )

}