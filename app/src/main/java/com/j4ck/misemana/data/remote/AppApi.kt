package com.j4ck.misemana.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppApi {
    private const val BASE_URL = "https://5b58d94d-cb0d-488f-b980-fc88af011baa.mock.pstmn.io"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun getHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()
    }

    fun createService(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}
