package com.iunctainc.iuncta.app.data.remote.api

import com.iunctainc.iuncta.app.data.beans.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface WelcomeApi {

    @POST(EndPoints.Auth.register)
    fun registerAsync(@Body user : RegisterUserRequest): Deferred<Response<CommanResponse<ArrayList<String?>>>>

    @POST(EndPoints.Auth.resend)
    fun resendAsync(@Body user : RegisterUserRequest): Deferred<Response<CommanResponse<ArrayList<String?>>>>

    @POST(EndPoints.Auth.resendOTP)
    fun resendOTPAsync(@Body user : RegisterUserRequest): Deferred<Response<LoginResponse>>

    @POST(EndPoints.Auth.login)
    fun loginAsync(@Body user : RegisterUserRequest): Deferred<Response<LoginResponse>>

    @POST(EndPoints.Auth.forgotPassword)
    fun forgotPasswordAsync(@Body user : RegisterUserRequest): Deferred<Response<CommanResponse<ArrayList<String?>>>>

    @POST(EndPoints.Auth.verifyOtp)
    fun verifyOTPAsync(@Body user : RegisterUserRequest):  Deferred<Response<CommanResponse<ArrayList<String?>>>>

}