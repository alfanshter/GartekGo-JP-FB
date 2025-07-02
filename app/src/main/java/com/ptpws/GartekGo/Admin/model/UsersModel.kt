package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class UsersModel(
    val email: String = "",
    val nama: String = "",
    val kelas: String = "",
    val nomor_absen: Int ? = null,
    val uid: String ? = null,
    val program_keahlian: String = "",
    val role: String = "user",
    val createdAt: com.google.firebase.Timestamp? = null
)
