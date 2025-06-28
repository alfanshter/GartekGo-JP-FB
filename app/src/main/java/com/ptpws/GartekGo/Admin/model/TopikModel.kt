package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp

data class TopikModel(
    var id: String = "",
    var nama: String ? = null,
    var semester : String = "",
    var file_materi : String ? = null,
    var nomor : Int ? = null,
    var nama_file : String? = null,
    var uploadedMateriAt : Timestamp? = null
)