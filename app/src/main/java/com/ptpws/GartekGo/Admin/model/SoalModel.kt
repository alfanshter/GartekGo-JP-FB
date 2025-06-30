package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class SoalModel(
    val soal: String = "",
    val id_soal: String = "",
    val jawaban: Map<String, String> = emptyMap(),
    val jawaban_benar: String = "",
    val topik: DocumentReference? = null
)
