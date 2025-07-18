package com.ptpws.GartekGo.Admin.Pages

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.model.ProjectUploadsModel
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenilaianScreen(navController: NavController, outerPadding: PaddingValues = PaddingValues()) {
    val context = LocalContext.current
    val db = Firebase.firestore

    val userList = remember { mutableStateListOf<ProjectUploadsModel>() }
    var isLoading by remember { mutableStateOf(true) }
    var isLoadingMore by remember { mutableStateOf(false) }
    var lastVisible by remember { mutableStateOf<DocumentSnapshot?>(null) }
    var endReached by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    // Tambahan: filter
    var selectedKelas by remember { mutableStateOf<String?>(null) }
    var selectedProgram by remember { mutableStateOf<String?>(null) }
    var selectedStatus by remember { mutableStateOf<Boolean?>(null) }


    val filteredList by remember(selectedKelas, selectedProgram, selectedStatus, userList) {
        derivedStateOf {
            userList.filter { user ->
                val matchKelas = selectedKelas?.let { user.kelas == it } ?: true
                val matchProgram = selectedProgram?.let { user.program_keahlian == it } ?: true
                val matchStatus = selectedStatus?.let { user.status == it } ?: true
                matchKelas && matchProgram
            }
        }
    }

    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    // Fetch awal
    LaunchedEffect(Unit) {
        fetchUsersPaged(
            db = db,
            userList = userList,
            limit = 10,
            lastVisible = null,
            onLastVisibleChanged = { lastVisible = it },
            onLoadingChanged = { isLoading = it },
            onEndReached = { endReached = it },
            selectedStatus = selectedStatus
        )
    }


    LaunchedEffect(selectedKelas, selectedProgram, selectedStatus) {
        userList.clear()
        endReached = false
        lastVisible = null

        fetchUsersPaged(
            db = db,
            userList = userList,
            limit = 10,
            lastVisible = null,
            selectedKelas = selectedKelas,
            selectedProgram = selectedProgram,
            selectedStatus = selectedStatus,
            onLastVisibleChanged = { lastVisible = it },
            onLoadingChanged = { isLoading = it },
            onEndReached = { endReached = it }
        )

    }

    // Scroll detect for auto-load
    LaunchedEffect(listState, selectedKelas, selectedProgram) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { index ->
                if (index != null && index >= userList.size - 3 && !isLoadingMore && !endReached) {
                    isLoadingMore = true
                    fetchUsersPaged(
                        db = db,
                        userList = userList,
                        limit = 10,
                        lastVisible = lastVisible,
                        selectedKelas = selectedKelas,
                        selectedProgram = selectedProgram,
                        onLastVisibleChanged = { lastVisible = it },
                        onLoadingChanged = { isLoadingMore = it },
                        onEndReached = { endReached = it },
                        selectedStatus = selectedStatus
                    )
                }
            }
    }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()) {
            isSearching = true
            searchUsers(
                db = db,
                query = searchQuery,
                onResult = {
                    userList.clear()
                    userList.addAll(it)
                    endReached = true // supaya paging tidak trigger saat search
                },
                onLoadingChanged = { isLoading = it },
            )
        } else {
            // Reset ke paging awal
            isSearching = false
            userList.clear()
            endReached = false
            lastVisible = null
            fetchUsersPaged(
                db = db,
                userList = userList,
                limit = 10,
                lastVisible = null,
                selectedKelas = selectedKelas,
                selectedProgram = selectedProgram,
                onLastVisibleChanged = { lastVisible = it },
                onLoadingChanged = { isLoading = it },
                onEndReached = { endReached = it },
                selectedStatus = selectedStatus
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "Penilaian Project",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xffF5F9FF)
                )
            )
        }, contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffF5F9FF))
                .padding(innerPadding)
                .padding(outerPadding)
        ) {
            // 🔽 Tambahan CHIP filter
            androidx.compose.material.OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Cari nama") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                CustomDropdownChip(
                    label = "Status",
                    options = listOf("-", "Sudah dinilai", "Belum dinilai"),
                    selectedOption = when (selectedStatus) {
                        null -> "Status"
                        true -> "Sudah dinilai"
                        false -> "Belum dinilai"
                    },
                    onOptionSelected = {
                        selectedStatus = when (it) {
                            "Status" -> null
                            "Sudah dinilai" -> true
                            "Belum dinilai" -> false
                            else -> null
                        }
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))
                CustomDropdownChip(
                    label = "Kelas",
                    options = listOf("-", "X", "XI", "XII"),
                    selectedOption = selectedKelas,
                    onOptionSelected = { selectedKelas = if (it == "-") null else it }
                )
                Spacer(modifier = Modifier.width(12.dp))
                CustomDropdownChip(
                    label = "Program Keahlian",
                    options = listOf("-", "TKP", "GEO", "DPIB"),
                    selectedOption = selectedProgram,
                    onOptionSelected = { selectedProgram = if (it == "-") null else it }
                )

            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                if (isLoading) {
                    items(5) {
                        ShimmerUserCard()
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                } else {
                    items(filteredList) { user ->
                        UserCard(navController, user)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    if (isLoadingMore) {
                        item {
                            ShimmerUserCard()
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

suspend fun fetchUsersPaged(
    db: FirebaseFirestore,
    userList: SnapshotStateList<ProjectUploadsModel>,
    limit: Long,
    lastVisible: DocumentSnapshot?,
    selectedKelas: String? = null,
    selectedProgram: String? = null,
    onLastVisibleChanged: (DocumentSnapshot?) -> Unit,
    onLoadingChanged: (Boolean) -> Unit,
    onEndReached: (Boolean) -> Unit,
    selectedStatus: Boolean? = null
) {

    onLoadingChanged(true)
    try {
        var query = db.collection("project_uploads")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .orderBy("uid")
            .limit(limit)


        if (selectedStatus != null) {
            query = query.whereEqualTo("status", selectedStatus)
        }

        if (selectedKelas != null) {
            query = query.whereEqualTo("kelas", selectedKelas)
        }
        if (selectedProgram != null) {
            query = query.whereEqualTo("program_keahlian", selectedProgram)
        }


        if (lastVisible != null) {
            query = query.startAfter(lastVisible)
        }

        val snapshot = query.get().await()
        println("📦 Data fetched: ${snapshot.documents.size}")

        if (!snapshot.isEmpty) {
            val newUsers = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ProjectUploadsModel::class.java)?.copy(uid = doc.id)
            }
            userList.addAll(newUsers)
            println("📈 userList.size = ${userList.size}")
            onLastVisibleChanged(snapshot.documents.last())
        } else {
            onEndReached(true)
        }
    } catch (e: Exception) {
        onEndReached(true)
        e.printStackTrace()
    }
    onLoadingChanged(false)
}

suspend fun searchUsers(
    db: FirebaseFirestore,
    query: String,
    onResult: (List<ProjectUploadsModel>) -> Unit,
    onLoadingChanged: (Boolean) -> Unit
) {
    onLoadingChanged(true)
    db.collection("project_uploads")
        .orderBy("nama")
        .get()
        .addOnSuccessListener { result ->
            val users = result.documents.mapNotNull {
                it.toObject(ProjectUploadsModel::class.java)?.copy(uid = it.id)
            }.filter {
                it.nama?.lowercase()?.contains(query.lowercase()) == true
            }
            onResult(users)
            onLoadingChanged(false)
        }
        .addOnFailureListener {
            onResult(emptyList())
            onLoadingChanged(false)
        }
}


@Composable
fun UserCard(navController: NavController, data: ProjectUploadsModel) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
//                navController.navigate(AppScreen.Home.Admin.NilaiSiswa.createRoute(user))

            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Topik ${data.nomor_topik} - ${data.nama_topik}",
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text("Nama: ${data.nama}")
            Text("Kelas: ${data.kelas} - ${data.program_keahlian}")
            Text("Nilai: ${data.nilai ?: "-"}")

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Lihat Gambar",
                    color = Color(0xFF167F71),
                    fontFamily = poppinsfamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        showDialog = true
                    }
                )

            }
        }
    }

    if (showDialog){
        ImagePopupDialog(
            imageUrl = data.imageUrl!!,
            onDismiss = {
                showDialog = false
            }, context = context,
            docId = data.id_project,
            nilaiInitial = data.nilai,
            keteranganInitial = data.keterangan
        )
    }

}

@Composable
fun ShimmerUserCard() {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray.copy(alpha = 0.3f))
            .shimmer(shimmer)
    )
}

@Composable
fun CustomDropdownChip(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (selectedOption != null) Color(0xFF2962FF) else Color.Gray
        ),
        color = Color.Transparent,
        modifier = Modifier
            .height(30.dp)
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedOption ?: label,
            fontWeight = if (selectedOption != null) FontWeight.Bold else FontWeight.Normal,
            fontFamily = poppinsfamily,
            fontSize = 11.sp,
            color = if (selectedOption != null) Color(0xFF2962FF) else Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = item,
                        fontWeight = if (item == selectedOption) FontWeight.Bold else FontWeight.Normal,
                        fontFamily = poppinsfamily
                    )
                },
                onClick = {
                    onOptionSelected(if (item == "-") null else item)
                    expanded = false
                }
            )
        }
    }
}

@Composable
fun ImagePopupDialog(
    imageUrl: String,
    nilaiInitial: Int?, // dikirim dari luar
    keteranganInitial: String?, // dikirim dari luar
    onDismiss: () -> Unit,
    context: Context,
    docId: String?
) {
    var nilai by remember { mutableStateOf("") }
    var keterangan by remember { mutableStateOf("") }

    // Inisialisasi nilai dan keterangan hanya sekali
    LaunchedEffect(Unit) {
        nilai = nilaiInitial?.toString() ?: ""
        keterangan = keteranganInitial ?: ""
    }

    var showFullImage by remember { mutableStateOf(false) }

    // Shared painter untuk loading state
    val painter = rememberAsyncImagePainter(model = imageUrl)
    val state = painter.state

    var isSubmitting by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                val nilaiInt = nilai.toIntOrNull()
                if (nilaiInt != null && nilaiInt in 0..100) {
                    isSubmitting = true
                    updateProjectUploadData(
                        docId = docId!!,
                        nilai = nilaiInt,
                        keterangan = keterangan,
                        onSuccess = {
                            isSubmitting = false
                            Toast.makeText(context, "✅ Nilai berhasil disimpan", Toast.LENGTH_SHORT).show()
                            onDismiss()
                        },
                        onError = {
                            isSubmitting = false
                            Toast.makeText(context, "❌ Gagal: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                    )
                } else {
                    Toast.makeText(context, "⚠️ Nilai harus antara 0 - 100", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Tutup")
            }
        },
        title = { Text("Detail Gambar") },
        text = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { showFullImage = true }
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Gambar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )



                    if (state is AsyncImagePainter.State.Loading) {
                        // 🛠️ Tampilkan loading hanya saat loading
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.3f))
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }


                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = {
                        val request = DownloadManager.Request(Uri.parse(imageUrl))
                            .setTitle("Download Gambar")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "gambar_${System.currentTimeMillis()}.jpg")
                        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                        dm.enqueue(request)
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Download Gambar")
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = nilai,
                    onValueChange = { nilai = it },
                    label = { Text("Nilai (0-100)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = keterangan,
                    onValueChange = { keterangan = it },
                    label = { Text("Keterangan") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )

    if (showFullImage) {

        Dialog(onDismissRequest = { showFullImage = false }) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                var scale by remember { mutableStateOf(1f) }
                var offset by remember { mutableStateOf(Offset.Zero) }

                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Full Image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                scale *= zoom
                                offset += pan
                            }
                        }
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                )


                IconButton(
                    onClick = { showFullImage = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        }
    }

}

fun updateProjectUploadData(
    docId: String,
    nilai: Int,
    keterangan: String,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val data = mapOf(
        "nilai" to nilai,
        "keterangan" to keterangan,
        "status" to true
    )

    db.collection("project_uploads")
        .document(docId)
        .update(data)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { onError(it) }
}




