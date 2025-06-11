import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VidioScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier, containerColor = Color(0xffF5F9FF), topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Topik 1 : MATERI",
                    fontWeight = FontWeight.Bold, fontFamily = poppinsfamily, fontSize = 24.sp, color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* back */ }) {
                    Icon(painter = painterResource(id = R.drawable.back),contentDescription = null, tint = Color.Unspecified)

                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White // TopAppBar background
            )
        )

    }
    ) { innerPadding ->
        Spacer(Modifier.height(40.dp))
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffF5F9FF))
        ) {
            item {
                val isInPreview = LocalInspectionMode.current
                if (!isInPreview) {
                    AndroidView(
                        factory = { context ->
                            WebView(context).apply {
                                webViewClient = WebViewClient()
                                settings.javaScriptEnabled = true
                                settings.loadWithOverviewMode = true
                                settings.useWideViewPort = true
                                loadUrl("https://www.youtube.com/embed/VIDEO_ID?autoplay=0&modestbranding=1&rel=0")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(220.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(220.dp)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Preview WebView", color = Color.DarkGray)
                    }
                }
            }

            item {
                Text(
                    text = "Tonton videonya sampai selesai untuk membuka tugas berikutnya",
                    fontSize = 16.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp, bottom = 32.dp)
                )
            }

            item {
                Button(
                    onClick = { /* TODO: aksi lanjut */ },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF005DFF),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(60.dp)
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
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
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
    VidioScreen()

}