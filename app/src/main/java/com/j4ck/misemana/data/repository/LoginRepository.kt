package com.j4ck.misemana.data.repository

import com.j4ck.misemana.data.remote.ApiInterface
import com.j4ck.misemana.data.remote.request.LoginRequest
import com.j4ck.misemana.data.remote.response.LoginResponse

class LoginRepository(val apiInterface: ApiInterface) {

    fun login(username: String, password: String): Result<LoginResponse> {
        val loginRequest = LoginRequest(username, password)

        val call = apiInterface.login(loginRequest)

        try {
            val response = call.execute() //Ejecuta la petición al instante por eso necesita que se ejecute fuera del hilo Main
            // Cuando la petición se completa
            //if (response.code() == 200) {
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse == null) {
                    return Result.failure(Exception("El body está vacío"))
                }
                return Result.success(loginResponse)
            } else {
                return Result.failure(IllegalAccessException())
            }
        } catch (e: Exception) {
            // Cuando la petición no se completa
            return Result.failure(e)
        }
    }

    fun logout() {

    }
}