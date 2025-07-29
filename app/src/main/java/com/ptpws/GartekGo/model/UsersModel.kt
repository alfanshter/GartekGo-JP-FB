package com.ptpws.GartekGo.model

import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.serializers.TimestampSerializer
import com.google.firebase.firestore.DocumentReference
import kotlinx.serialization.Serializable

@Serializable
data class UsersModel(
    val email: String = "",
    val nama: String = "",
    val kelas: String = "",
    val nomor_absen: Int ? = null,
    val uid: String ? = null,
    val program_keahlian: String = "",
    val role: String = "user",
    @Serializable(with = TimestampSerializer::class)
    val createdAt: com.google.firebase.Timestamp? = null
)
