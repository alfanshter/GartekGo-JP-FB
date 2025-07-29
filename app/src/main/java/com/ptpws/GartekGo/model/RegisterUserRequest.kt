package com.ptpws.GartekGo.model

data class RegisterUserRequest(
    val email: String,
    val nama: String,
    val kelas: String,
    val nomorAbsen: String,
    val programKeahlian: String
)
