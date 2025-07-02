package com.ptpws.GartekGo.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    val registerService = "https://registeruser-yhq6g2q4sa-uc.a.run.app/"
    val updateService = "https://updateuser-yhq6g2q4sa-uc.a.run.app/"
    val deleteService = "https://deleteuser-yhq6g2q4sa-uc.a.run.app/"

    private val services = mutableMapOf<String, UserApiService>()

    fun getService(baseUrl: String): UserApiService {
        return services.getOrPut(baseUrl) {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApiService::class.java)
        }
    }
}
