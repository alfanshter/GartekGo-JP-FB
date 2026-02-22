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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.model.TopikModel
import com.ptpws.GartekGo.model.VideoItem

data class VideoUploadItem(
    val uri: Uri? = null,
    val uploadedData: VideoItem? = null,
    val isNew: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TambahVidioDialogNew(
    onDismis: () -> Unit,
    semester: Int,
    onSave: (TopikModel) -> Unit,
    topikModel: TopikModel? = null,
) {
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) }
    var uploadingIndex by remember { mutableStateOf(-1) }

    val context = LocalContext.current

    // List untuk menyimpan video items (kombinasi yang sudah diupload dan yang baru)
    val videoItems = remember {
        mutableStateListOf<VideoUploadItem>().apply {
            // Load existing videos jika ada
            topikModel?.videos?.sortedBy { it.urutan }?.forEach { videoItem ->
                add(VideoUploadItem(
                    uri = null,
                    uploadedData = videoItem,
                    isNew = false
                ))
            }
        }
    }

    var selectedVideoIndex by remember { mutableStateOf(-1) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null && selectedVideoIndex >= 0) {
            videoItems[selectedVideoIndex] = VideoUploadItem(
                uri = uri,
                uploadedData = null,
                isNew = true
            )
        } else if (uri != null) {
            // Tambah video baru
            videoItems.add(VideoUploadItem(
                uri = uri,
                uploadedData = null,
                isNew = true
            ))
        }
        selectedVideoIndex = -1
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
                    Text(
                        "Daftar Video",
                        fontFamily = poppinsfamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // List video items
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        itemsIndexed(videoItems) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            "Video ${index + 1}",
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            when {
                                                item.uri != null -> getVideoFileNameFromUri(context, item.uri)
                                                item.uploadedData != null -> item.uploadedData.nama ?: "Video"
                                                else -> "Belum dipilih"
                                            },
                                            fontFamily = poppinsfamily,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    Row {
                                        // Button ganti video
                                        IconButton(onClick = {
                                            selectedVideoIndex = index
                                            launcher.launch("video/*")
                                        }) {
                                            Icon(
                                                painter = androidx.compose.ui.res.painterResource(id = com.ptpws.GartekGo.R.drawable.ic_launcher_foreground),
                                                contentDescription = "Ganti",
                                                tint = Color.Blue
                                            )
                                        }

                                        // Button hapus
                                        IconButton(onClick = {
                                            videoItems.removeAt(index)
                                        }) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Hapus",
                                                tint = Color.Red
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tombol tambah video
                    Button(
                        onClick = {
                            launcher.launch("video/*")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D5CFF)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Tambah Video",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Tombol simpan
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                if (videoItems.isEmpty()) {
                                    Toast.makeText(context, "Tambahkan minimal 1 video", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                isUploading = true
                                uploadMultipleVideos(
                                    context = context,
                                    videoItems = videoItems,
                                    topikId = topikModel?.id ?: "",
                                    onProgress = { index, progress ->
                                        uploadingIndex = index
                                        uploadProgress = progress
                                    },
                                    onSuccess = { uploadedVideos ->
                                        isUploading = false
                                        val newTopik = topikModel?.copy(videos = uploadedVideos)
                                            ?: TopikModel(videos = uploadedVideos)
                                        onSave(newTopik)
                                        Toast.makeText(context, "Semua video berhasil diupload!", Toast.LENGTH_SHORT).show()
                                        onDismis()
                                    },
                                    onFailure = { error ->
                                        isUploading = false
                                        Toast.makeText(context, "Upload gagal: ${error.message}", Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
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
            title = { Text("Mengunggah Video ${uploadingIndex + 1}/${videoItems.size}...") },
            text = {
                Column {
                    LinearProgressIndicator(progress = uploadProgress / 100f, modifier = Modifier.fillMaxWidth())
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${uploadProgress.toInt()}%", textAlign = TextAlign.Center)
                }
            },
            confirmButton = {}
        )
    }
}

fun uploadMultipleVideos(
    context: Context,
    videoItems: List<VideoUploadItem>,
    topikId: String,
    onProgress: (index: Int, progress: Float) -> Unit,
    onSuccess: (List<VideoItem>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val uploadedVideos = mutableListOf<VideoItem>()
    var currentIndex = 0

    fun uploadNext() {
        if (currentIndex >= videoItems.size) {
            // Semua video selesai diupload, simpan ke Firestore
            saveVideosToFirestore(
                topikId = topikId,
                videos = uploadedVideos,
                onSuccess = { onSuccess(uploadedVideos) },
                onFailure = onFailure
            )
            return
        }

        val item = videoItems[currentIndex]

        if (item.isNew && item.uri != null) {
            // Upload video baru
            uploadVideoToFirebase(
                context = context,
                uri = item.uri,
                onSuccess = { url, path ->
                    uploadedVideos.add(
                        VideoItem(
                            url = url,
                            nama = getVideoFileNameFromUri(context, item.uri),
                            path = path,
                            urutan = currentIndex
                        )
                    )
                    currentIndex++
                    uploadNext()
                },
                onFailure = onFailure,
                onProgress = { progress ->
                    onProgress(currentIndex, progress)
                }
            )
        } else if (!item.isNew && item.uploadedData != null) {
            // Video sudah diupload sebelumnya, gunakan data yang ada
            uploadedVideos.add(item.uploadedData.copy(urutan = currentIndex))
            currentIndex++
            uploadNext()
        } else {
            currentIndex++
            uploadNext()
        }
    }

    uploadNext()
}

fun uploadVideoToFirebase(
    context: Context,
    uri: Uri,
    onSuccess: (downloadUrl: String, pathName: String) -> Unit,
    onFailure: (Exception) -> Unit,
    onProgress: (Float) -> Unit
) {
    val storage = FirebaseStorage.getInstance()

    val contentResolver = context.contentResolver
    val type = contentResolver.getType(uri)
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(type) ?: "mp4"

    val fileName = "${System.currentTimeMillis()}.$extension"
    val storageRef = storage.reference.child("public/$fileName")
    val pathName = "public/$fileName"
    val uploadTask = storageRef.putFile(uri)

    uploadTask
        .addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
            onProgress(progress)
        }
        .addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                onSuccess(downloadUrl.toString(), pathName)
            }
        }
        .addOnFailureListener {
            onFailure(it)
        }
}

fun saveVideosToFirestore(
    topikId: String,
    videos: List<VideoItem>,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val firestore = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"

    // Convert VideoItem list to map list for Firestore
    val videosMap = videos.map { video ->
        hashMapOf(
            "url" to video.url,
            "nama" to video.nama,
            "path" to video.path,
            "urutan" to video.urutan
        )
    }

    val data = hashMapOf(
        "videos" to videosMap,
        "uploadedMateriBy" to userId,
        "uploadedMateriAt" to FieldValue.serverTimestamp()
    )

    firestore.collection("topik").document(topikId).update(data as Map<String, Any>)
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { onFailure(it) }
}

