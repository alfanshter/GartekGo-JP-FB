package com.ptpws.GartekGo.Admin.model

data class RegisterUserRequest(
    val email: String,
    val nama: String,
    val kelas: String,
    val nomorAbsen: String,
    val programKeahlian: String
)
