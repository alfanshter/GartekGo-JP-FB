package com.ptpws.GartekGo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ptpws.GartekGo.Admin.model.TopikModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class AppScreen(@StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {
    object Home : AppScreen(R.string.home, R.drawable.home, "home" ){
        object Semester : AppScreen(R.string.semester, R.drawable.piala, "semester"){
            object Topik : AppScreen(R.string.semester, R.drawable.piala, "topik"){
                object Materi : AppScreen(R.string.semester, R.drawable.piala, "materi")
                object Vidio : AppScreen(R.string.semester, R.drawable.piala, "vidio")
                object Soal: AppScreen(R.string.semester, R.drawable.piala, "soal")
                object Upload: AppScreen(R.string.semester, R.drawable.piala, "upload")
                //bottombar


            }



        }
        object Admin : AppScreen(R.string.admin, R.drawable.home, "admin"){
            object TambahTopik : AppScreen(R.string.admin,R.drawable.topik,"tambahtopik")
            object TambahPembelajaran : AppScreen(R.string.admin,R.drawable.topik,"tambahpembelajaran")
            object TambahMateri : AppScreen(R.string.admin,R.drawable.tambahmaterii,"tambahmateri")
            object TambahVidio : AppScreen(R.string.admin,R.drawable.tambahvidioo,"tambahvidio")
            object TambahSoal : AppScreen(R.string.admin,R.drawable.tambahsoall,"tambahsoal")
            object TambahProject : AppScreen(R.string.admin,R.drawable.penilaiann,"tambahproject")
            object TambahSiswa : AppScreen(R.string.admin,R.drawable.siswa,"tambahsiswa")
            object TambahSoalProject : AppScreen(R.string.admin,R.drawable.siswa,"tambahsoalproject")
            object Penilaian : AppScreen(R.string.admin,R.drawable.siswa,"penilaian")
            object NilaiSiswa : AppScreen(R.string.admin,R.drawable.siswa,"nilaisiswa")

            object DataSoal: AppScreen(R.string.datasoal, R.drawable.piala, "datasoal/{jsonTopik}"){
                fun createRoute(topikModel: TopikModel): String {
                    val json = Json.encodeToString(topikModel)
                    val encoded = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                    return "datasoal/$encoded"

                }
            }


        }
    }


    object Nilai : AppScreen(R.string.nilai, R.drawable.piala, "nilai")
    object Profile : AppScreen(R.string.profil, R.drawable.profilenav, "profile")



}