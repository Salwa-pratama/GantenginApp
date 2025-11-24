package com.gantenginapp.apps.remote

import android.R
import android.os.Message
import  retrofit2.http.GET
import com.gantenginapp.apps.model.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val status: String,
    val data: User?
)

data class RegisterResponse(
    val message: String,
    val status: String,
    val data: User?
)

data class RegistStoreRequest(
    val id :String,
    val name : String,
    val noHp : String,
    val jamBuka : String,
    val jamTutup : String
)

data class RegistStoreResponse(
    val message: String,
    val status: String,
    val data: RegistStoreRequest?
)


data class AllStores (
    val id_store : String,
    val id_user : String,
    val name : String,
    val noHp : String,
    val jamBuka : String,
    val jamTutup : String,
    val price : Int
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
    @POST("auth/regist")
    suspend fun register(@Body request: User): RegisterResponse

    @POST("auth/regist-store")
    suspend fun registStore(@Body request: RegistStoreRequest) : RegistStoreResponse

    @GET("auth/get-user/{id}")
    suspend fun getUser(@Path("id") id : String) : User

    // GetAllDataStore
    @GET("auth/all-store")
    suspend fun getAllStore(): List<AllStores>
}




