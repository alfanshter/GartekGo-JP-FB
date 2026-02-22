import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.media3.common.MediaItem

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import com.ptpws.GartekGo.Semester.FullScreenVideoActivity
import com.ptpws.GartekGo.model.VideoItem
import kotlinx.coroutines.tasks.await


@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VidioScreen(navController: NavController, idtopik: String) {
    val db = Firebase.firestore
    val context = LocalContext.current

    var videos by remember { mutableStateOf<List<VideoItem>>(emptyList()) }
    var currentVideoIndex by remember { mutableStateOf(0) }
    var watchedVideoCount by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }
    var showButton by remember { mutableStateOf(false) }
    var showCompletionDialog by remember { mutableStateOf(false) }
    var allVideosCompleted by remember { mutableStateOf(false) }

    val uid = FirebaseAuth.getInstance().uid

    // Load video list dan status progress
    LaunchedEffect(idtopik) {
        try {
            val topikSnapshot = db.collection("topik").document(idtopik).get().await()

            // Ambil videos array
            val videosData = topikSnapshot.get("videos") as? List<Map<String, Any>>
            if (videosData != null) {
                videos = videosData.mapIndexed { index, map ->
                    VideoItem(
                        url = map["url"] as? String,
                        nama = map["nama"] as? String,
                        path = map["path"] as? String,
                        urutan = (map["urutan"] as? Long)?.toInt() ?: index
                    )
                }.sortedBy { it.urutan }
            } else {
                // Fallback untuk old single video format
                val videoUrl = topikSnapshot.getString("file_video")
                val videoNama = topikSnapshot.getString("nama_video")
                if (videoUrl != null) {
                    videos = listOf(
                        VideoItem(
                            url = videoUrl,
                            nama = videoNama ?: "Video",
                            path = null,
                            urutan = 0
                        )
                    )
                }
            }

            // Get status progress - berapa video yang sudah ditonton
            val userTopikSnapshot = db.collection("users")
                .document(uid!!)
                .collection("topik")
                .document(idtopik)
                .get().await()

            val videosWatchedCount = (userTopikSnapshot.getLong("videos_watched") ?: 0).toInt()
            watchedVideoCount = videosWatchedCount
            currentVideoIndex = videosWatchedCount.coerceAtMost(videos.size - 1)

            val status = userTopikSnapshot.getString("vidio")
            showButton = status == "1"
            allVideosCompleted = videosWatchedCount >= videos.size

            isLoading = false
        } catch (e: Exception) {
            Log.e("VIDIO_SCREEN", "Error: ${e.message}")
            isLoading = false
        }
    }

    Scaffold(
        modifier = Modifier, containerColor = Color(0xffF5F9FF), topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "VIDIO",
                        fontWeight = FontWeight.Bold,
                        fontFamily = poppinsfamily,
                        fontSize = 24.sp,
                        color = Color.Black
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
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xffF5F9FF)
                )
            )
        }, contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val exoPlayerRef = remember { mutableStateOf<ExoPlayer?>(null) }
            val currentPosition = remember { mutableStateOf(0L) }

            // Cleanup ExoPlayer when leaving screen
            DisposableEffect(Unit) {
                onDispose {
                    exoPlayerRef.value?.stop()
                    exoPlayerRef.value?.release()
                    exoPlayerRef.value = null
                }
            }

            // Reset player position when video changes
            LaunchedEffect(currentVideoIndex) {
                currentPosition.value = 0L
            }

            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val lastPosition = result.data?.getLongExtra("lastPosition", 0L) ?: 0L

                    // Check apakah video selesai di fullscreen (position >= duration)
                    val videoDuration = exoPlayerRef.value?.duration ?: 0L
                    val isVideoCompleted = videoDuration > 0 && lastPosition >= videoDuration - 1000 // toleransi 1 detik

                    if (isVideoCompleted) {
                        // Video selesai di fullscreen - trigger onVideoEnded
                        val currentIndex = currentVideoIndex
                        if (currentIndex >= watchedVideoCount) {
                            // Update progress
                            isLoading = true
                            val newWatchedCount = currentIndex + 1
                            val data = hashMapOf(
                                "videos_watched" to newWatchedCount,
                                "vidio" to if (newWatchedCount >= videos.size) "1" else "0"
                            )
                            db.collection("users").document(uid!!)
                                .collection("topik").document(idtopik)
                                .set(data, SetOptions.merge())
                                .addOnSuccessListener {
                                    isLoading = false
                                    watchedVideoCount = newWatchedCount

                                    if (newWatchedCount >= videos.size) {
                                        // Semua video selesai
                                        allVideosCompleted = true
                                        showButton = true
                                        showCompletionDialog = true
                                    } else {
                                        // Lanjut ke video berikutnya
                                        currentVideoIndex = newWatchedCount
                                    }
                                }
                                .addOnFailureListener {
                                    isLoading = false
                                    Log.e("VIDIO_SCREEN", "Error update: ${it.message}")
                                }
                        }
                    } else {
                        // Video belum selesai - lanjutkan dari posisi terakhir
                        currentPosition.value = lastPosition
                        exoPlayerRef.value?.seekTo(lastPosition)
                        exoPlayerRef.value?.playWhenReady = true
                    }
                }
            }

            if (videos.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Render setiap video secara berurutan
                    items(videos.size) { index ->
                        val video = videos[index]
                        val isWatched = index < watchedVideoCount
                        val canWatch = index <= watchedVideoCount
                        val isCurrentlyPlaying = index == currentVideoIndex

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCurrentlyPlaying) Color(0xFFE3F2FD) else Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                // Header video dengan nomor dan status
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Nomor video
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .background(
                                                color = when {
                                                    isWatched -> Color(0xFF00C853)
                                                    canWatch -> Color(0xFF3D5CFF)
                                                    else -> Color.Gray
                                                },
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isWatched) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        } else {
                                            Text(
                                                text = "${index + 1}",
                                                color = Color.White,
                                                fontFamily = poppinsfamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 18.sp
                                            )
                                        }
                                    }

                                    Spacer(Modifier.width(12.dp))

                                    // Nama video
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = video.nama ?: "Video ${index + 1}",
                                            fontFamily = poppinsfamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = when {
                                                isWatched -> "✅ Selesai"
                                                canWatch -> "▶️ Tonton sekarang"
                                                else -> "🔒 Terkunci"
                                            },
                                            fontFamily = poppinsfamily,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }

                                Spacer(Modifier.height(12.dp))

                                // Video player atau locked message
                                if (canWatch && video.url != null) {
                                    // Tampilkan player hanya untuk video yang currentVideoIndex
                                    if (index == currentVideoIndex) {
                                        ExoPlayerWithFullscreenYouTubeStyle(
                                            storagePath = video.url!!,
                                            exoPlayerRef = exoPlayerRef,
                                            startPosition = currentPosition.value,
                                            onVideoEnded = {
                                                // Video selesai ditonton - hanya update jika ini video baru (belum ditonton)
                                                if (index >= watchedVideoCount) {
                                                    // Update progress
                                                    isLoading = true
                                                    val newWatchedCount = index + 1
                                                    val data = hashMapOf(
                                                        "videos_watched" to newWatchedCount,
                                                        "vidio" to if (newWatchedCount >= videos.size) "1" else "0"
                                                    )
                                                    db.collection("users").document(uid!!)
                                                        .collection("topik").document(idtopik)
                                                        .set(data, SetOptions.merge())
                                                        .addOnSuccessListener {
                                                            isLoading = false
                                                            watchedVideoCount = newWatchedCount

                                                            if (newWatchedCount >= videos.size) {
                                                                // Semua video selesai
                                                                allVideosCompleted = true
                                                                showButton = true
                                                                showCompletionDialog = true
                                                            } else {
                                                                // Lanjut ke video berikutnya
                                                                currentVideoIndex = newWatchedCount
                                                            }
                                                        }
                                                        .addOnFailureListener {
                                                            isLoading = false
                                                            Log.e("VIDIO_SCREEN", "Error update: ${it.message}")
                                                        }
                                                }
                                            },
                                            onFullscreenClick = {
                                                val intent = Intent(context, FullScreenVideoActivity::class.java)
                                                val position = exoPlayerRef.value?.currentPosition ?: 0L
                                                intent.putExtra("videoUrl", video.url)
                                                intent.putExtra("startPosition", position)

                                                currentPosition.value = position
                                                exoPlayerRef.value?.playWhenReady = false
                                                launcher.launch(intent)
                                            }
                                        )
                                    } else {
                                        // Video yang tidak sedang aktif - tampilkan placeholder
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(16f / 9f)
                                                .background(
                                                    if (isWatched) Color(0xFF00C853).copy(alpha = 0.1f)
                                                    else Color(0xFF3D5CFF).copy(alpha = 0.1f),
                                                    RoundedCornerShape(12.dp)
                                                )
                                                .clickable {
                                                    // Pindah ke video ini
                                                    currentVideoIndex = index
                                                    currentPosition.value = 0L
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    imageVector = if (isWatched) Icons.Default.CheckCircle else Icons.Default.PlayArrow,
                                                    contentDescription = null,
                                                    tint = if (isWatched) Color(0xFF00C853) else Color(0xFF3D5CFF),
                                                    modifier = Modifier.size(48.dp)
                                                )
                                                Spacer(Modifier.height(8.dp))
                                                Text(
                                                    if (isWatched) "Video Selesai" else "Tonton Video",
                                                    fontFamily = poppinsfamily,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 16.sp,
                                                    color = if (isWatched) Color(0xFF00C853) else Color(0xFF3D5CFF)
                                                )
                                                Text(
                                                    "Tap untuk ${if (isWatched) "tonton ulang" else "play"}",
                                                    fontFamily = poppinsfamily,
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    }
                                } else {
                                    // Video terkunci
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(16f / 9f)
                                            .background(Color.Gray.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Lock,
                                                contentDescription = null,
                                                tint = Color.Gray,
                                                modifier = Modifier.size(48.dp)
                                            )
                                            Spacer(Modifier.height(8.dp))
                                            Text(
                                                "Video Terkunci",
                                                fontFamily = poppinsfamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp,
                                                color = Color.Gray
                                            )
                                            Text(
                                                "Selesaikan video sebelumnya",
                                                fontFamily = poppinsfamily,
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }

                                // Progress indicator untuk video yang sedang ditonton
                                if (index == currentVideoIndex && !isWatched) {
                                    Spacer(Modifier.height(12.dp))
                                    Text(
                                        "Progress: Video ${index + 1} dari ${videos.size}",
                                        fontFamily = poppinsfamily,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    LinearProgressIndicator(
                                        progress = { (watchedVideoCount + 1).toFloat() / videos.size },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }

                    // Spacer untuk bottom button
                    item {
                        Spacer(Modifier.height(80.dp))
                    }
                }
            } else {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (showCompletionDialog) {
                AlertDialog(
                    onDismissRequest = { showCompletionDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showCompletionDialog = false
                        }) {
                            Text("OK")
                        }
                    },
                    title = {
                        Text(text = "Semua Video Selesai!")
                    },
                    text = {
                        Text("Selamat! Anda telah menyelesaikan semua video. Silakan lanjut ke soal.")
                    }
                )
            }

            if (showButton && allVideosCompleted) {
                Button(
                    onClick = { navController.navigate("${AppScreen.Home.Semester.Topik.Soal.route}/$idtopik") },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF005DFF), contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "SELANJUTNYA",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                                .size(48.dp)
                        ) {
                            Card(
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(0.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = "Next",
                                        tint = Color(0xFF0057FF),
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun VidioScreenPreview() {
    VidioScreen(navController = rememberNavController(), idtopik = "")

}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ExoPlayerWithFullscreenYouTubeStyle(
    storagePath: String,
    exoPlayerRef: MutableState<ExoPlayer?>,
    startPosition: Long,
    onVideoEnded: () -> Unit,
    onFullscreenClick: () -> Unit
) {
    val context = LocalContext.current

    // Create player synchronously using remember with key
    val exoPlayer = remember(storagePath) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(storagePath))
            prepare()
            if (startPosition > 0) {
                seekTo(startPosition)
            }
            playWhenReady = true
        }
    }

    // Update ref
    exoPlayerRef.value = exoPlayer

    // Handle lifecycle and video end detection
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoEnded()
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = true
                    controllerShowTimeoutMs = 5000
                    controllerHideOnTouch = false
                    setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)

                    // Disable semua bentuk seeking
                    setShowRewindButton(false)      // Hilangkan tombol rewind
                    setShowFastForwardButton(false) // Hilangkan tombol fast forward
                    setShowPreviousButton(false)    // Hilangkan tombol previous
                    setShowNextButton(false)        // Hilangkan tombol next

                    // Disable progress bar interaction
                    post {
                        // Cari dan disable TimeBar (progress bar)
                        disableSeekingRecursively(this)
                    }
                }
            },
            update = { playerView ->
                playerView.player = exoPlayer
                playerView.post {
                    disableSeekingRecursively(playerView)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Fullscreen button overlay
        IconButton(
            onClick = { onFullscreenClick() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(36.dp)
                .background(Color(0x66000000), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Fullscreen,
                contentDescription = "Fullscreen",
                tint = Color.White
            )
        }
    }
}

// Helper function untuk disable seeking pada semua child views
private fun disableSeekingRecursively(view: View) {
    // Jika ini adalah DefaultTimeBar, disable touch
    if (view.javaClass.name.contains("TimeBar") || view.javaClass.name.contains("DefaultTimeBar")) {
        view.isEnabled = false
        view.isClickable = false
        view.isFocusable = false
        view.setOnTouchListener { _, _ -> true } // Consume semua touch events
    }

    // Jika ViewGroup, iterate semua children
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            disableSeekingRecursively(view.getChildAt(i))
        }
    }
}



fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}





