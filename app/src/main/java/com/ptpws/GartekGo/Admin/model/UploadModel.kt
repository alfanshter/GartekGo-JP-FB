package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp

data class UploadModel(
    val uid: String = "",
    val id_topik: String = "",
    val imageUrl: String = "",
    val timestamp: Timestamp = Timestamp.now()
)