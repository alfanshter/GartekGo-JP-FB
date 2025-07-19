package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.serializers.TimestampSerializer
import kotlinx.serialization.Serializable

@Serializable
data class ProjectUploadsModel(
    val id_project: String? = null, // ← ini harus dari `doc.id`
    val id_topik : String? = null,
    val imageUrl : String? = null,
    val nilai : Int? = null,
    val status : Boolean? = null,
    @Serializable(with = TimestampSerializer::class)
    val timestamp: Timestamp? = null,
    val uid : String? = null,
    val nama : String? = null,
    val kelas : String? = null,
    val program_keahlian : String? = null,
    val nama_topik : String? = null,
    val nomor_topik : Int? = null,
    val keterangan : String? = null,
)
