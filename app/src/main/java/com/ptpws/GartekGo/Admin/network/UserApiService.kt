package com.ptpws.GartekGo.Admin.network

import com.ptpws.GartekGo.Admin.model.RegisterUserRequest
import com.ptpws.GartekGo.Admin.model.RegisterUserResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface UserApiService {
    @Headers("Content-Type: application/json")
    @POST("registerUser") // Path dari Firebase Function
    suspend fun registerUser(@Body request: RegisterUserRequest): RegisterUserResponse
}
