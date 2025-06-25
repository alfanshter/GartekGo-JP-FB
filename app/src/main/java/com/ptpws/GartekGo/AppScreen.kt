package com.ptpws.GartekGo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class AppScreen(@StringRes val title: Int, @DrawableRes val icon: Int, val route: String) {
    object Home : AppScreen(R.string.home, R.drawable.home, "home" ){
        object Semester : AppScreen(R.string.semester, R.drawable.piala, "semester"){
            object Topik : AppScreen(R.string.semester, R.drawable.piala, "topik"){
                object Materi : AppScreen(R.string.semester, R.drawable.piala, "materi")
                object Vidio : AppScreen(R.string.semester, R.drawable.piala, "vidio")
                object Soal: AppScreen(R.string.semester, R.drawable.piala, "soal")
                object Upload: AppScreen(R.string.semester, R.drawable.piala, "soal")
                //bottombar
                object Beranda: AppScreen(R.string.semester, R.drawable.piala, "soal")


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


        }
    }


    object Nilai : AppScreen(R.string.nilai, R.drawable.piala, "nilai")
    object Profile : AppScreen(R.string.profil, R.drawable.profilenav, "profile")



}