package com.smart.sample.app.data.repo.welcome

import com.smart.sample.app.data.beans.*
import com.smart.sample.app.data.remote.helper.ApiCallback
import retrofit2.Response

interface WelcomeRepo {

    fun createAccount(req: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
    fun resend(req: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
    fun resendOTP(req: RegisterUserRequest, apiCallback: ApiCallback<Response<LoginResponse>>)
    fun login(req: RegisterUserRequest, apiCallback: ApiCallback<Response<LoginResponse>>)
    fun forgotPassword(req: RegisterUserRequest, apiCallback:ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
    fun verifyOTP(req: RegisterUserRequest, apiCallback:ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
}