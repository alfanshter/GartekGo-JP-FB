package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp

data class TopikModel(
    val id: String = "",
    val nama: String = "",
    val semester : String = "",
    val file_materi : String = "",
    val nama_materi : String? = null,
    val uploadedMateriAt : Timestamp? = null
)