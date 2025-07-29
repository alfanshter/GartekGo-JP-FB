package com.ptpws.GartekGo.model

import com.google.firebase.Timestamp

data class NilaiGabungan(
    val namaTopik: String,
    val nomorTopik: Int,
    val nilaiSoal: Int?,       // bisa null kalau tidak ada
    val nilaiProject: Int?     // bisa null kalau tidak ada
)
