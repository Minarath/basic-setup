package com.smart.sample.app.data.repo.welcome

import com.google.gson.Gson
import com.smart.sample.app.data.beans.CommanResponse
import com.smart.sample.app.data.beans.LoginResponse
import com.smart.sample.app.data.beans.RegisterUserRequest
import com.smart.sample.app.data.beans.base.SimpleApiResponse
import com.smart.sample.app.data.local.SharedPref
import com.smart.sample.app.data.remote.api.WelcomeApi
import com.smart.sample.app.data.remote.helper.ApiCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class WelcomeRepoImpl(private val welcomeApi: WelcomeApi, private val sharedPref: SharedPref) : WelcomeRepo {

    override fun createAccount(user: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = welcomeApi.registerAsync(user)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let {
                            apiCallback.onFailed(it)
                        }
                                ?: let { apiCallback.onFailed("Something went wrong") }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        apiCallback.onErrorThrow(e)
                    }
                }
            }
        }
    }


    override fun resend(user: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = welcomeApi.resendAsync(user)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let {
                            apiCallback.onFailed(it)
                        }
                                ?: let { apiCallback.onFailed("Something went wrong") }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        apiCallback.onErrorThrow(e)
                    }
                }
            }
        }
    }

    override fun resendOTP(user: RegisterUserRequest, apiCallback: ApiCallback<Response<LoginResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = welcomeApi.resendOTPAsync(user)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let {
                            apiCallback.onFailed(it)
                        }
                                ?: let { apiCallback.onFailed("Something went wrong") }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        apiCallback.onErrorThrow(e)
                    }
                }
            }
        }
    }


    override fun login(user: RegisterUserRequest, apiCallback: ApiCallback<Response<LoginResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = welcomeApi.loginAsync(user)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let {
                            apiCallback.onFailed(it)
                        }
                                ?: let { apiCallback.onFailed("Something went wrong") }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        apiCallback.onErrorThrow(e)
                    }
                }
            }
        }
    }

    override fun forgotPassword(user: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = welcomeApi.forgotPasswordAsync(user)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let {
                            apiCallback.onFailed(it)
                        }
                                ?: let { apiCallback.onFailed("Something went wrong") }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        apiCallback.onErrorThrow(e)
                    }
                }
            }
        }
    }

    override fun verifyOTP(user: RegisterUserRequest, apiCallback: ApiCallback<Response<CommanResponse<ArrayList<String?>>>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = welcomeApi.verifyOTPAsync(user)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let {
                            apiCallback.onFailed(it)
                        }
                                ?: let { apiCallback.onFailed("Something went wrong") }
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        apiCallback.onErrorThrow(e)
                    }
                }
            }
        }
    }


}