import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.media3.common.MediaItem

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
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
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.Player
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
import kotlinx.coroutines.tasks.await


@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VidioScreen(navController: NavController, idtopik: String) {
    val db = Firebase.firestore
    val context = LocalContext.current
    var videoUrl by remember { mutableStateOf<String?>(null) }
    var videoFinished by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var showButton by remember { mutableStateOf(false) }
    var showCompletionDialog by remember { mutableStateOf(false) }

    val uid = FirebaseAuth.getInstance().uid

    // Load video URL dan status progress
    LaunchedEffect(idtopik) {
        try {
            // Get video URL
            val topikSnapshot = db.collection("topik").document(idtopik).get().await()
            videoUrl = topikSnapshot.getString("path_video")

            // Get status progress
            val userTopikSnapshot = db.collection("users")
                .document(uid!!)
                .collection("topik")
                .document(idtopik)
                .get().await()

            val status = userTopikSnapshot.getString("vidio")
            showButton = status == "1"

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
                        text = "Topik 1 : VIDIO",
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
            if (videoUrl != null) {
                ExoPlayerWithFullscreenYouTubeStyle(storagePath = videoUrl!!) {
                    if (!showButton) {
                        isLoading = true
                        // Update progress ke Firestore
                        val data = hashMapOf("vidio" to "1")
                        db.collection("users").document(uid!!)
                            .collection("topik").document(idtopik)
                            .set(data, SetOptions.merge())
                            .addOnSuccessListener {
                                isLoading = false
                                showCompletionDialog = true
                                showButton = true
                            }
                            .addOnFailureListener {
                                isLoading = false
                                Log.e("VIDIO_SCREEN", "Error update: ${it.message}")
                            }
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
                        Text(text = "Video Selesai")
                    },
                    text = {
                        Text("Anda telah selesai menonton video.")
                    }
                )
            }

            if (showButton) {
                Button(
                    onClick = {  },
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
                                        imageVector = Icons.Default.ArrowForward,
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
    onVideoEnded: () -> Unit
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val storage = Firebase.storage
    var videoUrl by remember { mutableStateOf<String?>(null) }
    var isFullscreen by remember { mutableStateOf(false) }
    var hasEnded by remember { mutableStateOf(false) }

    LaunchedEffect(storagePath) {
        try {
            val url = storage.reference.child(storagePath).downloadUrl.await().toString()
            videoUrl = url
        } catch (e: Exception) {
            Log.e("VideoPlayer", "Gagal ambil URL: ${e.message}")
        }
    }

    if (videoUrl == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl!!))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED && !hasEnded) {
                    hasEnded = true
                    onVideoEnded()
                }
            }
        }

        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Normal mode
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
    ) {
        AndroidView(
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = {
                isFullscreen = true
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            },
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

    if (isFullscreen) {
        Dialog(
            onDismissRequest = {
                isFullscreen = false
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            BackHandler {
                isFullscreen = false
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                AndroidView(
                    factory = {
                        PlayerView(context).apply {
                            player = exoPlayer
                            useController = true
//                            systemUiVisibility = (
//                                    View.SYSTEM_UI_FLAG_FULLSCREEN or
//                                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
//                                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                                    )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                IconButton(
                    onClick = {
                        isFullscreen = false
                        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .size(40.dp)
                        .background(Color(0x66000000), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Exit Fullscreen",
                        tint = Color.White
                    )
                }
            }
        }
    }
}



fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}





