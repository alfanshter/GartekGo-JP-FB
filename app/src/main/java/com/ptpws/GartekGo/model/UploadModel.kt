package com.ptpws.GartekGo.model

import com.google.firebase.Timestamp

data class UploadModel(
    val uid: String = "",
    val nama: String = "",
    val kelas: String = "",
    val program_keahlian: String = "",
    val id_topik: String = "",
    val nama_topik: String = "",
    val nomor_topik: Int = 0,
    val imageUrl: String ? = null,
    val timestamp: Timestamp = Timestamp.now(),
    val status: Boolean = false,
    val id_project: String = "",
    val nilai: Int? = null,
    val semester: Int? = null
)