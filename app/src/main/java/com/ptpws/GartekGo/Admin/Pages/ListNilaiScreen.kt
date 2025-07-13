import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.model.UsersModel
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ListNilaiScreen(navController: NavController, outerPadding: PaddingValues = PaddingValues()) {
    val context = LocalContext.current
    val chipLabelsnilai = listOf("Kelas", "Program Keahlian")


    var showDropdown by remember { mutableStateOf(false) }
    var selectedChipIndex by remember { mutableStateOf(-1) }
    var activeDropdownLabel by remember { mutableStateOf<String?>(null) }

    var selectedKelas by remember { mutableStateOf<String?>(null) }
    var selectedProgram by remember { mutableStateOf<String?>(null) }

    val userList = remember { mutableStateListOf<UsersModel>() }



    // AMBIL DATA DARI FIRESTORE
    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                userList.clear()
                for (doc in result.documents) {
                    val user = doc.toObject(UsersModel::class.java)?.copy(uid = doc.id)
                    if (user != null) {
                        userList.add(user)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(
                    context,
                    "Gagal ambil data: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


    val filteredUserList by remember(selectedKelas, selectedProgram, userList) {
        derivedStateOf {
            userList.filter { user ->
                val matchKelas = selectedKelas?.let { user.kelas == it } ?: true
                val matchProgram = selectedProgram?.let { user.program_keahlian == it } ?: true
                matchKelas && matchProgram
            }
        }
    }


    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    windowInsets = WindowInsets(0),
                    title = {
                        Text(
                            text = "Nilai",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = poppinsfamily,
                            color = Color.Black
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xffF5F9FF)
                    )
                )

            }
        }, contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        val combinedPadding = PaddingValues(
            top = innerPadding.calculateTopPadding(),
            bottom = outerPadding.calculateBottomPadding()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xffF5F9FF))
                .padding(combinedPadding)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Chip filter
                CustomDropdownChip(
                    label = "Kelas",
                    options = listOf("-", "X", "XI", "XII"),
                    selectedOption = selectedKelas,
                    onOptionSelected = { selectedKelas = it }
                )

                Spacer(Modifier.height(15.dp))

                CustomDropdownChip(
                    label = "Program Keahlian",
                    options = listOf("-", "RPL", "TKJ", "DKV"),
                    selectedOption = selectedProgram,
                    onOptionSelected = { selectedProgram = it }
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text("Hasil Filter (${filteredUserList.size} data)", fontWeight = FontWeight.Bold)

            // ListNilaiComponent dengan hasil filter
            ListNilaiComponent(navController, filteredUserList)
        }



        }
    }


@Preview(showBackground = true)
@Composable
private fun ListNilaiScreenPreview() {
    ListNilaiScreen(navController = rememberNavController())
}

@Composable
fun ListNilaiComponent(navController: NavController, userList: List<UsersModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF5F9FF))
    ) {
        items(userList) { data ->
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth().clickable{ navController.navigate(AppScreen.Home.Admin.NilaiSiswa.route) }
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            text = "Nama :",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${data.nama}",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                    }
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            text = "Email :",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = " ${data.email}",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                    }
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            text = "No. Absen :",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "${data.nomor_absen}",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                    }
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            text = "Kelas :",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${data.kelas}",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                    }

                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text(
                            text = "Program Keahlian :",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${data.program_keahlian}",
                            fontFamily = poppinsfamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp, color = Color.Black
                        )
                    }


                }
            }
            Spacer(Modifier.height(7.dp))

        }
    }
}

@Composable
fun CustomDropdownChip(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(vertical = 4.dp)
    ) {
        // Chip-nya
        Surface(
            shape = RoundedCornerShape(50),
            border = BorderStroke(
                2.dp,
                if (selectedOption != null) Color(0xFF2962FF) else Color.Gray
            ),
            color = Color.White,
            modifier = Modifier
                .clickable { expanded = !expanded }
        ) {
            Text(
                text = if (selectedOption != null) "$label: $selectedOption" else label,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                color = if (selectedOption != null) Color(0xFF2962FF) else Color.Gray,
                fontWeight = if (selectedOption != null) FontWeight.Bold else FontWeight.Normal
            )
        }

        // Dropdown-nya versi list terbuka di bawah chip
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, Color.LightGray)
                    .padding(4.dp)
            ) {
                options.forEach { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onOptionSelected(if (item == "-") null else item)
                                expanded = false
                            }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        color = if (item == selectedOption) Color(0xFF2962FF) else Color.Black,
                        fontWeight = if (item == selectedOption) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
