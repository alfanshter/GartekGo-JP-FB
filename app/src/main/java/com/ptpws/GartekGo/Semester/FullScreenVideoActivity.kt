package com.ptpws.GartekGo.Semester

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.ptpws.GartekGo.Semester.ui.theme.GartekGoTheme
class FullScreenVideoActivity : ComponentActivity() {

    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ambil data dari intent
        val videoUrl = intent.getStringExtra("videoUrl")
        val startPosition = intent.getLongExtra("startPosition", 0L)

        // Set ke landscape + fullscreen
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        setContent {
            MaterialTheme {
                if (videoUrl != null) {
                    FullscreenVideoPlayer(videoUrl, startPosition) { position ->
                        // Balik ke sebelumnya bawa posisi terakhir
                        val resultIntent = Intent().apply {
                            putExtra("lastPosition", position)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }
                } else {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No video URL provided")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun FullscreenVideoPlayer(videoUrl: String, startPosition: Long, onExit: (Long) -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            seekTo(startPosition)
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    // Video selesai - set position ke duration agar dianggap selesai
                    onExit(exoPlayer.duration)
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    BackHandler {
        onExit(exoPlayer.currentPosition)
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = true
                controllerShowTimeoutMs = 5000
                controllerHideOnTouch = false
                setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)

                // Disable semua bentuk seeking
                setShowRewindButton(false)
                setShowFastForwardButton(false)
                setShowPreviousButton(false)
                setShowNextButton(false)

                // Disable progress bar interaction
                post {
                    disableSeekingRecursivelyFullscreen(this)
                }

                systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

// Helper function untuk disable seeking pada semua child views
private fun disableSeekingRecursivelyFullscreen(view: View) {
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
            disableSeekingRecursivelyFullscreen(view.getChildAt(i))
        }
    }
}

