package com.ptpws.GartekGo.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference


data class NilaiProjectModel(
    val id_topik: DocumentReference? = null,
    val imageUrl: String? = null,
    val uid: String? = null,
    val nilai: Int ?=null,
    val status: Boolean ?=null,
    val timestamp: Timestamp? = null
)