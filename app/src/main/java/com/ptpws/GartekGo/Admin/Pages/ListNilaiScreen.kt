import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.ptpws.GartekGo.Admin.model.UsersModel
import com.ptpws.GartekGo.AppScreen
import com.ptpws.GartekGo.Commond.poppinsfamily
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

@Composable
fun ListNilaiScreen(navController: NavController, outerPadding: PaddingValues = PaddingValues()) {
    val context = LocalContext.current
    val db = Firebase.firestore

    val userList = remember { mutableStateListOf<UsersModel>() }
    var isLoading by remember { mutableStateOf(true) }
    var isLoadingMore by remember { mutableStateOf(false) }
    var lastVisible by remember { mutableStateOf<DocumentSnapshot?>(null) }
    var endReached by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    // Tambahan: filter
    var selectedKelas by remember { mutableStateOf<String?>(null) }
    var selectedProgram by remember { mutableStateOf<String?>(null) }


    val filteredList by remember(selectedKelas, selectedProgram, userList) {
        derivedStateOf {
            userList.filter { user ->
                val matchKelas = selectedKelas?.let { user.kelas == it } ?: true
                val matchProgram = selectedProgram?.let { user.program_keahlian == it } ?: true
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
            onEndReached = { endReached = it }
        )
    }

    LaunchedEffect(selectedKelas, selectedProgram) {
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
                        onEndReached = { endReached = it }
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
                onLoadingChanged = { isLoading = it }
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
                onEndReached = { endReached = it }
            )
        }
    }



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = "Nilai",
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
            OutlinedTextField(
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
            Text(
                "Hasil (${filteredList.size} data)",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

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
    userList: SnapshotStateList<UsersModel>,
    limit: Long,
    lastVisible: DocumentSnapshot?,
    selectedKelas: String? = null,
    selectedProgram: String? = null,
    onLastVisibleChanged: (DocumentSnapshot?) -> Unit,
    onLoadingChanged: (Boolean) -> Unit,
    onEndReached: (Boolean) -> Unit
) {

    onLoadingChanged(true)
    try {
        var query = db.collection("users")
            .orderBy("nama")
            .limit(limit)

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
                doc.toObject(UsersModel::class.java)?.copy(uid = doc.id)
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

fun searchUsers(
    db: FirebaseFirestore,
    query: String,
    onResult: (List<UsersModel>) -> Unit,
    onLoadingChanged: (Boolean) -> Unit
) {
    onLoadingChanged(true)
    db.collection("users")
        .orderBy("nama")
        .get()
        .addOnSuccessListener { result ->
            val users = result.documents.mapNotNull {
                it.toObject(UsersModel::class.java)?.copy(uid = it.id)
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
fun UserCard(navController: NavController, user: UsersModel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(AppScreen.Home.Admin.NilaiSiswa.createRoute(user))

            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Nama: ${user.nama}")
            Text("Email: ${user.email}")
            Text("Kelas: ${user.kelas}")
            Text("Program: ${user.program_keahlian}")
        }
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


