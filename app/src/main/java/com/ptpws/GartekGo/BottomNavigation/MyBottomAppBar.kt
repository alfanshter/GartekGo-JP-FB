import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ptpws.GartekGo.Admin.Pages.HomeAdmin
import com.ptpws.GartekGo.Admin.Pages.NilaiSiswa
import com.ptpws.GartekGo.Admin.Pages.TambahTopikScreen
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.ptpws.GartekGo.HomeScreen.ProfileScreen
import com.ptpws.GartekGo.R
import com.ptpws.GartekGo.Semester.ProjectScreen
import com.ptpws.GartekGo.Semester.SemesterScreen
import com.ptpws.GartekGo.Semester.SoalScreen
import com.ptpws.GartekGo.Semester.TampilanNilaiSiswa
import com.ptpws.GartekGo.Semester.UploadScreen

@Composable
fun MyBottomAppBar(navController: NavController) {
    val navigationController = rememberNavController()

    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    // Atur warna Navigation Bar Sistem
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color(0xffF5F9FF),
            darkIcons = true
        )
    }


    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,

            ) {
                // Beranda
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Home
                        navigationController.navigate(AppScreen.Home.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.home),
                            contentDescription = "Beranda",
                            modifier = Modifier.size(24.dp),
                            tint = if (selected.value == Icons.Default.Home)
                                colorResource(id = R.color.biru)
                            else
                                colorResource(R.color.abuabu)
                        )
                        Text(
                            text = "Beranda",
                            fontSize = 11.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Medium,
                            color = if (selected.value == Icons.Default.Home)
                                colorResource(id = R.color.biru)
                            else
                                colorResource(R.color.abuabu)
                        )
                    }
                }

                // Nilai
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.AddCard
                        navigationController.navigate(AppScreen.Nilai.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.piala),
                            contentDescription = "Nilai",
                            modifier = Modifier.size(24.dp),
                            tint = if (selected.value == Icons.Default.AddCard)
                                colorResource(id = R.color.biru)
                            else
                                colorResource(R.color.abuabu)
                        )
                        Text(
                            text = "Nilai",
                            fontSize = 11.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Medium,
                            color = if (selected.value == Icons.Default.AddCard)
                                colorResource(id = R.color.biru)
                            else
                                colorResource(R.color.abuabu)
                        )
                    }
                }

                // Profil
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Notifications
                        navigationController.navigate(AppScreen.Profile.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.profilenav),
                            contentDescription = "Profil",
                            modifier = Modifier.size(24.dp),
                            tint = if (selected.value == Icons.Default.Notifications)
                                colorResource(id = R.color.biru)
                            else
                                colorResource(R.color.abuabu)
                        )
                        Text(
                            text = "Profil",
                            fontSize = 11.sp, fontFamily = poppinsfamily, fontWeight = FontWeight.Medium,
                            color = if (selected.value == Icons.Default.Notifications)
                                colorResource(id = R.color.biru)
                            else
                                colorResource(R.color.abuabu)
                        )
                    }
                }

            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()
        ){
            NavHost(
                navController = navigationController,
                startDestination = AppScreen.Home.route,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(AppScreen.Home.route) { HomeScreen(navigationController) }
//                composable(AppScreen.Home.Semester.Topik.Nilai.route) { NilaiSiswa(navigationController) }
                composable(AppScreen.Home.Semester.route) { SemesterScreen(navigationController, onTambahClick = {}, pilihan = 0) }
//                composable(AppScreen.Home.Semester.Topik.route) { TopikScreen(navigationController) }
                composable(
                    route = "${AppScreen.Home.Semester.Topik.route}/{idTopik}",
                    arguments = listOf(navArgument("idTopik") { type = NavType.StringType })
                ) {
                    val idTopik = it.arguments?.getString("idTopik")
                    TopikScreen(navigationController,idTopik.toString())
                }
                composable(
                    route = "${AppScreen.Home.Semester.Topik.Materi.route}/{idTopik}",
                    arguments = listOf(navArgument("idTopik") { type = NavType.StringType })
                ) {
                    val idTopik = it.arguments?.getString("idTopik")
                    MateriScreen(navigationController, idtopik = idTopik ?: "")
                }
                composable(
                    "${AppScreen.Home.Semester.Topik.Vidio.route}/{idtopik}",
                    arguments = listOf(navArgument("idtopik") { type = NavType.StringType })
                ) {
                    val idtopik = it.arguments?.getString("idtopik") ?: ""
                    VidioScreen(navigationController, idtopik)
                }
                composable(
                    route = "${AppScreen.Home.Semester.Topik.Soal.route}/{idtopik}",
                    arguments = listOf(navArgument("idtopik") { type = NavType.StringType })
                ) { backStackEntry ->
                    val idtopik = backStackEntry.arguments?.getString("idtopik") ?: ""
                    SoalScreen(navigationController, idtopik)
                }
                composable(
                    route = "${AppScreen.Home.Semester.Topik.Upload.route}/{idtopik}",
                    arguments = listOf(navArgument("idtopik") { type = NavType.StringType })
                ) {
                    val idtopik = it.arguments?.getString("idtopik") ?: ""
                    UploadScreen(navController = navigationController, idtopik = idtopik)
                }

                composable(AppScreen.Nilai.route) { TampilanNilaiSiswa( navigationController) }
                composable(AppScreen.Profile.route) { ProfileScreen(navigationController) }

                //admin
                composable(AppScreen.Home.Admin.TambahTopik.route) { TambahTopikScreen(navigationController) }


            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun MyBottomAppBarrPreview() {

        MyBottomAppBar(navController = rememberNavController())

}