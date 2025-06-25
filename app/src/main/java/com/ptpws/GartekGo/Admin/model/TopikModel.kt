package com.ptpws.GartekGo.Admin.model

import com.google.firebase.Timestamp

data class TopikModel(
    var id: String = "",
    var nama: String = "",
    var semester : String = "",
    var file_materi : String = "",
    var nama_materi : String? = null,
    var uploadedMateriAt : Timestamp? = null
)