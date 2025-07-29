package com.ptpws.GartekGo.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference


data class NilaiModel(
    val uid: String = "",
    val semester: String = "",
    val topik: DocumentReference? = null,
    val nilai: Int = 0,
    val jawaban_siswa: List<String> = emptyList(),
    val jawaban_benar: List<String> = emptyList(),
    val benar_siswa: Int = 0,
    val total_soal: Int = 0,
    val status_lulus: String = "", // "LULUS" atau "TIDAK LULUS"
    val timestamp: Timestamp? = null
)