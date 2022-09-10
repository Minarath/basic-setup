package com.iunctainc.iuncta.app.data.repo.welcome

import com.iunctainc.iuncta.app.data.beans.*
import com.iunctainc.iuncta.app.data.remote.helper.ApiCallback
import retrofit2.Response

interface WelcomeRepo {

    fun createAccount(req: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
    fun resend(req: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
    fun resendOTP(req: RegisterUserRequest, apiCallback: ApiCallback<Response<LoginResponse>>)
    fun login(req: RegisterUserRequest, apiCallback: ApiCallback<Response<LoginResponse>>)
    fun forgotPassword(req: RegisterUserRequest, apiCallback:ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
    fun verifyOTP(req: RegisterUserRequest, apiCallback:ApiCallback<Response<CommanResponse<ArrayList<String?>>>>)
}