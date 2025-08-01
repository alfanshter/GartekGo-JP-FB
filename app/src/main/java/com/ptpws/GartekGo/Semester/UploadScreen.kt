package com.ptpws.GartekGo.Semester

import android.R.attr.button
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ptpws.GartekGo.model.UploadModel
import com.ptpws.GartekGo.Commond.jostfamily
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.Dialog.SuccessDialog
import com.ptpws.GartekGo.R
import com.ptpws.GartekGo.Utils
import java.io.ByteArrayOutputStream

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    navController: NavController,
    idtopik: String
) {
    var gambarsuksesdikirim by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showActionButtons by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }
    var uploadProgress by remember { mutableStateOf(0f) }
    var uploadedUrl by remember { mutableStateOf<String?>(null) }
    var nomorTopik by remember { mutableStateOf(0) }
    var sudahUpload by remember { mutableStateOf(false) } //Tambah
    var isLoadingGambar by remember { mutableStateOf(true) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = Utils.saveBitmapToCache(context, it)
            imageUri = uri
            showActionButtons = false
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            showActionButtons = false
        }
    }

    fun fetchLastUploadedImage() {
        isLoadingGambar = true
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("project_uploads")
            .whereEqualTo("uid", user.uid)
            .whereEqualTo("id_topik", idtopik)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val lastDoc = documents.documents[0]
                    val url = lastDoc.getString("imageUrl")
                    if (url != null) {
                        uploadedUrl = url
                        sudahUpload = true
                        imageUri = null
                    }
                }
                isLoadingGambar = false
            }
            .addOnFailureListener {
                Log.e("UploadScreen", "Gagal mengambil gambar terakhir: ${it.message}")
                isLoadingGambar = false
            }
    }


    fun fetchTopikInfo() {
        val db = FirebaseFirestore.getInstance()
        db.collection("topik").document(idtopik).get()
            .addOnSuccessListener { dataTopik ->
                nomorTopik = dataTopik.getLong("nomor")?.toInt() ?: 0
                Log.d("UploadScreen", "Nomor Topik: $nomorTopik")
            }
            .addOnFailureListener {
                Log.e("UploadScreen", "Gagal ambil data topik: ${it.message}")
            }
    }

    LaunchedEffect(true) {
        fetchLastUploadedImage()
        fetchTopikInfo()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "Topik $nomorTopik",
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsfamily,
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xffF5F9FF))
            )
        }, contentWindowInsets = WindowInsets(0),
        containerColor = Color(0xFFF7F9FC)
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xffF5F9FF))
        ) {
            val (imageBox, actionButtons, iconRow, button, notifText) = createRefs()

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .clickable {
                        if (imageUri != null && !sudahUpload) {
                            showActionButtons = !showActionButtons
                        }
                    }
                    .constrainAs(imageBox) {
                        top.linkTo(parent.top, margin = 100.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (uploadedUrl != null && sudahUpload) {
                    // Kalau sudah upload, selalu tampilkan gambar terakhir
                    Image(
                        painter = rememberAsyncImagePainter(uploadedUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (imageUri != null) {
                    // Saat user baru pilih gambar sebelum upload
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (isLoadingGambar) {
                    // Saat sedang loading data
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = Color.Gray
                    )
                } else {
                    // Default placeholder
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Placeholder",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                }


            }

            if (!sudahUpload) {
                if (showActionButtons) {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .constrainAs(actionButtons) {
                                top.linkTo(imageBox.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { galleryLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A651)),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.edit), contentDescription = null, tint = Color.Unspecified)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("EDIT", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                imageUri = null
                                showActionButtons = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Icon(painter = painterResource(id = R.drawable.hapus), tint = Color.Unspecified, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("HAPUS", fontFamily = jostfamily, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 18.sp)
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .constrainAs(iconRow) {
                                top.linkTo(imageBox.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { cameraLauncher.launch(null) },
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFF00C853), shape = RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Camera",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        IconButton(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color(0xFF00C853), shape = RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = "Gallery",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (imageUri == null) {
                            Toast.makeText(context, "Pilih gambar dulu", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val user = FirebaseAuth.getInstance().currentUser
                        if (user == null) {
                            Toast.makeText(context, "User belum login", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        try {
                            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                            val baos = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                            val bytes = baos.toByteArray()

                            val storageRef = FirebaseStorage.getInstance().reference
                            val fileName = "${user.uid}_${System.currentTimeMillis()}.jpg"
                            val imageRef = storageRef.child("public/project_gambar/$fileName")

                            val uploadTask = imageRef.putBytes(bytes)
                            isUploading = true

                            uploadTask
                                .addOnProgressListener { taskSnapshot ->
                                    val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toFloat()
                                    uploadProgress = progress
                                }
                                .addOnSuccessListener {
                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                        val db = FirebaseFirestore.getInstance()
                                        val uid = user.uid

                                        //ambil data users
                                        db.collection("users").document(uid).get()
                                            .addOnSuccessListener { document ->
                                                val nama = document.getString("nama") ?: ""
                                                val kelas = document.getString("kelas") ?: ""
                                                val program_keahlian =
                                                    document.getString("program_keahlian") ?: ""


                                                val upload = UploadModel(
                                                    uid = user.uid,
                                                    kelas = kelas,
                                                    nama = nama,
                                                    program_keahlian = program_keahlian,
                                                    id_topik = idtopik,
                                                    imageUrl = uri.toString(),
                                                    timestamp = Timestamp.now()
                                                )

                                                // Ambil data topik dari ID
                                                db.collection("topik").document(idtopik).get()
                                                    .addOnSuccessListener { topikDoc ->
                                                        if (topikDoc != null && topikDoc.exists()) {
                                                            val namaTopik =
                                                                topikDoc.getString("nama") ?: ""
                                                            val nomorTopik =
                                                                topikDoc.getLong("nomor") ?: 0L
                                                            val semester =
                                                                topikDoc.getLong("semester") ?: 0L

                                                            // Buat UploadModel dengan nama topik & nomor
                                                            val upload = UploadModel(
                                                                uid = uid,
                                                                nama = nama,
                                                                kelas = kelas,
                                                                program_keahlian = program_keahlian,
                                                                id_topik = idtopik,
                                                                nama_topik = namaTopik, // tambahkan di model
                                                                nomor_topik = nomorTopik.toInt(), // tambahkan di model
                                                                imageUrl = uri.toString(),
                                                                timestamp = Timestamp.now(),
                                                                status = false,
                                                                semester = semester.toInt(),
                                                            )


                                                            db.collection("project_uploads")
                                                                .add(upload)
                                                                .addOnSuccessListener { documentRef ->
                                                                    val idProject = documentRef.id // Ambil ID dokumen

                                                                    // Update field id_project di dokumen yang barusan dibuat
                                                                    db.collection("project_uploads").document(idProject)
                                                                        .update("id_project", idProject)
                                                                        .addOnSuccessListener {
                                                                            isUploading = false
                                                                            uploadProgress = 0f
                                                                            gambarsuksesdikirim = true
                                                                            sudahUpload = true //Untuk disable tombol
                                                                            fetchLastUploadedImage()
                                                                        }
                                                                        .addOnFailureListener {
                                                                            isUploading = false
                                                                            uploadProgress = 0f
                                                                            Toast.makeText(
                                                                                context,
                                                                                "Gagal update id_project",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                        }
                                                                }
                                                                .addOnFailureListener {
                                                                    isUploading = false
                                                                    uploadProgress = 0f
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Gagal simpan URL Firestore",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }

                                                        }
                                                    }
                                            }
                                        }
                                    }
                                .addOnFailureListener {
                                    isUploading = false
                                    uploadProgress = 0f
                                    Toast.makeText(context, "Gagal upload gambar", Toast.LENGTH_SHORT).show()
                                }

                        } catch (e: Exception) {
                            isUploading = false
                            uploadProgress = 0f
                            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0961F5), contentColor = Color.White),
                    modifier = Modifier
                        .constrainAs(button) {
                            if (showActionButtons) {
                                top.linkTo(actionButtons.bottom, margin = 48.dp)
                            } else {
                                top.linkTo(iconRow.bottom, margin = 48.dp)
                            }
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "KIRIM",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = poppinsfamily,
                    )
                }
            } else {
                Text(
                    text = "Anda sudah upload project gambar untuk topik ini",
                    color = Color(0xFFE53935),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsfamily, textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .constrainAs(notifText) {
                            top.linkTo(imageBox.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
            }

            if (gambarsuksesdikirim) {
                SuccessDialog(
                    onDismiss = {
                        gambarsuksesdikirim = false
                        navController.popBackStack()
                    }
                )
            }
        }
    }

    if (isUploading) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Mengunggah Gambar...") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(progress = uploadProgress / 100f)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${uploadProgress.toInt()}%", textAlign = TextAlign.Center)
                }
            },
            confirmButton = {}
        )
    }
}












@Preview(showBackground = true)
@Composable
private fun UploadScreenPreview() {
    UploadScreen(navController = rememberNavController(), idtopik = "")
    
}


