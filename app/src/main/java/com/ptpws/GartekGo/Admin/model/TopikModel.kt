package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.serializers.TimestampSerializer
import kotlinx.serialization.Serializable

@Serializable
data class TopikModel(
    var id: String = "",
    var nama: String ? = null,
    var semester : String = "",
    var file_materi : String ? = null,
    var nomor : Int ? = null,
    var nama_file : String? = null,
    @Serializable(with = TimestampSerializer::class)
    var uploadedMateriAt : Timestamp? = null,
    var file_video : String ? = null,
    var nama_video : String? = null,
    var path_video : String? = null,

    @Serializable(with = TimestampSerializer::class)
    var uploadedGambarProjectAt : Timestamp? = null,
    var soal_project : String? = null,
    var gambar_project : String? = null,
    var nama_file_project : String? = null,

    val jumlahSoal: Int = 0,
    val status_soal: Int = 0,
    )