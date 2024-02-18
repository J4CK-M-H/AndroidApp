package com.j4ck.misemana.data.remote

import com.j4ck.misemana.data.remote.request.LoginRequest
import com.j4ck.misemana.data.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}