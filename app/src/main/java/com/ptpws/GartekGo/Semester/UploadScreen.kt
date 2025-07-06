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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ptpws.GartekGo.Admin.model.UploadModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.Dialog.SuccessDialog
import com.ptpws.GartekGo.R
import com.ptpws.GartekGo.Utils
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(
    navController: NavController,
    idtopik: String
) {
    var gambarsuksesdikirim by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = Utils.saveBitmapToCache(context, it)
            imageUri = uri
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Upload Gambar",
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
        },
        containerColor = Color(0xFFF7F9FC)
    ) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xffF5F9FF))
        ) {
            val (imageBox, iconRow, button) = createRefs()

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .constrainAs(imageBox) {
                        top.linkTo(parent.top, margin = 100.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Placeholder",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

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

                        imageRef.putBytes(bytes)
                            .addOnSuccessListener {
                                imageRef.downloadUrl.addOnSuccessListener { uri ->
                                    val db = FirebaseFirestore.getInstance()
                                    val upload = UploadModel(
                                        uid = user.uid,
                                        id_topik = idtopik,
                                        imageUrl = uri.toString(),
                                        timestamp = Timestamp.now()
                                    )
                                    db.collection("project_uploads")
                                        .add(upload)
                                        .addOnSuccessListener {
                                            gambarsuksesdikirim = true
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(context, "Gagal simpan URL Firestore", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Gagal upload gambar", Toast.LENGTH_SHORT).show()
                            }

                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }

                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0961F5), contentColor = Color.White),
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom, margin = 24.dp)
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
}


@Preview(showBackground = true)
@Composable
private fun UploadScreenPreview() {
    UploadScreen(navController = rememberNavController(), idtopik = "")
    
}


