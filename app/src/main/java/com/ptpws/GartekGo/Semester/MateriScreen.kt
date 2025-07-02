import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.R
import com.rajat.pdfviewer.HeaderData
import com.rajat.pdfviewer.PdfRendererView
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import com.rajat.pdfviewer.util.PdfSource
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MateriScreen(navController: NavController, idtopik: String) {
    val db = Firebase.firestore
    val context = LocalContext.current
    var isReadComplete by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var materi by remember { mutableStateOf("") }
    var showButton by remember { mutableStateOf(false) }
    var showCompletionDialog by remember { mutableStateOf(false) }
    var uid = FirebaseAuth.getInstance().uid
    LaunchedEffect(idtopik) {
        try {
            val topikSnapshot = Firebase.firestore
                .collection("topik").document(idtopik)
                .get().await()

            val file = topikSnapshot.getString("file_materi")
            if (!file.isNullOrBlank()) {
                materi = file
            } else {
                Log.e("PDF_VIEWER", "file_materi kosong atau null")
            }

            val userTopikSnapshot = Firebase.firestore
                .collection("users").document(uid.toString())
                .collection("topik").document(idtopik)
                .get().await()

            val status = userTopikSnapshot.getString("materi")
            showButton = status == "1"

        } catch (e: Exception) {
            Log.e("PDF_VIEWER", "Error Firestore: ${e.message}")
        }
    }



    Scaffold(
        modifier = Modifier, containerColor = Color(0xffC2D8FF), topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "Topik 1 : MATERI",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = poppinsfamily,
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
                    containerColor = Color(0xffF5F9FF) // TopAppBar background
                )
            )

        }, contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (materi.isNotBlank()) {
                key(materi) {
                    PdfRendererViewCompose(
                        source = PdfSource.Remote(materi),
                        lifecycleOwner = LocalLifecycleOwner.current,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        headers = HeaderData(mapOf("Authorization" to "123456789")),
                        statusCallBack = object : PdfRendererView.StatusCallBack {
                            override fun onPdfLoadStart() {
                                isLoading = true
                            }

                            override fun onPdfRenderSuccess() {
                                isLoading = false
                            }

                            @androidx.annotation.OptIn(UnstableApi::class)
                            override fun onPageChanged(currentPage: Int, totalPage: Int) {
                                isReadComplete = currentPage == totalPage
                                if (isReadComplete && !showButton) {
                                    isLoading = true
                                    //upload data ke firestore
                                    val uid = FirebaseAuth.getInstance().uid

                                    val data = hashMapOf(
                                        "materi" to "1"
                                    )

                                    Firebase.firestore.collection("users").document(uid!!)
                                        .collection("topik").document(idtopik)
                                        .set(data)
                                        .addOnSuccessListener {
                                            isLoading = false
                                            showCompletionDialog = true
                                            showButton = true
                                        }
                                        .addOnFailureListener {
                                            isLoading = false
                                            Log.d("dinda", it.message.toString())
                                        }
                                }
                            }

                            override fun onError(error: Throwable) {
                                isLoading = false
                                Log.e("PDF_VIEWER", "Error: ${error.message}")
                            }
                        },
                        zoomListener = object : PdfRendererView.ZoomListener {
                            override fun onZoomChanged(isZoomedIn: Boolean, scale: Float) {}
                        }
                    )
                }
            }


            // Show loading overlay
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (showCompletionDialog) {
                AlertDialog(
                    onDismissRequest = { showCompletionDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            showCompletionDialog = false
                            // Arahkan ke halaman lain jika perlu:
                            // navController.navigate("NextScreen")
                        }) {
                            Text("OK")
                        }
                    },
                    title = {
                        Text(text = "Materi Selesai")
                    },
                    text = {
                        Text("Anda telah selesai membaca materi.")
                    }
                )
            }

            if (showButton) {
                Button(
                    onClick = { navController.navigate("${AppScreen.Home.Semester.Topik.Vidio.route}/$idtopik") },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF005DFF), contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(60.dp).align(Alignment.BottomCenter)
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
//        PdfReaderScreen(
//            pdfUrl = "https://firebasestorage.googleapis.com/v0/b/gartekgo.firebasestorage.app/o/public%2F1750749752849.pdf?alt=media&token=d59758a6-bcd5-49f3-8fc2-d75d974f158f",
//            navController = navController,
//            innerPadding = innerPadding // atau dari Scaffold
//        )

    }
}


@Preview(showBackground = true)
@Composable
private fun MateriScreenPreview() {
    MateriScreen(navController = rememberNavController(), idtopik = "")

}



