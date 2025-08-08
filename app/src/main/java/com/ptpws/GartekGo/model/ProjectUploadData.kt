package com.ptpws.GartekGo.model

import kotlinx.serialization.Serializable

@Serializable
data class ProjectUploadData(
    val status: Boolean?,
    val nilai: Int?,
    val imageUrl: String?,
    val idTopik: String?,
    val id_project: String?
)