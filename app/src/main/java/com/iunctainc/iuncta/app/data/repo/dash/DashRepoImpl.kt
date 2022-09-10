package com.iunctainc.iuncta.app.data.repo.dash

import com.google.gson.Gson
import com.iunctainc.iuncta.app.data.beans.*
import com.iunctainc.iuncta.app.data.beans.base.ApiResponse
import com.iunctainc.iuncta.app.data.beans.base.SimpleApiResponse
import com.iunctainc.iuncta.app.data.local.SharedPref
import com.iunctainc.iuncta.app.data.remote.api.DashApi
import com.iunctainc.iuncta.app.data.remote.helper.ApiCallback
import com.iunctainc.iuncta.app.ui.main.models.SmartSaleLoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class DashRepoImpl(private val dashApi: DashApi, private val sharedPref: SharedPref) : DashRepo {
    override fun doLogin(email: String, password: String, apiCallback: ApiCallback<Response<SmartSaleLoginResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.doLoginAsync(email,password)
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


    override fun getUserInfo(apiCallback: ApiCallback<Response<LoginResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.getUserInfoAsync()
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

    override fun changeUserName(user: RequestUsernameChange, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.changeUserNameAsync(user)
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

    override fun sendEmail(user: RequestEmailChange, apiCallback: ApiCallback<Response<SendOtpResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.sendOtpAsync(user)
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

    override fun verifyOtp(user: VerifyEmailRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.verifyOtpAsync(user)
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

    override fun changeEmail(user: ChangeEmailRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.changeEmailAsync(user)
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

    override fun sendOtpToPhone(user: SendOtpToPhoneRequest, apiCallback: ApiCallback<Response<SendPhoneOtpResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.sendOtpToPhoneAsync(user)
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

    override fun verifyPhoneOtp(user: VerifyEmailRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.verifyOtpPhoneAsync(user)
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

    override fun getUserProfiles(apiCallback: ApiCallback<Response<UserProfileList>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.getUserProfileListAsync()
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

    override fun setDefaultProfile(user: SetDefaultProfile, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.setDefaultProfileAsync(user)
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

    override fun getHelpList(apiCallback: ApiCallback<Response<HelpListResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.getHelpListAsync()
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

    override fun sendHelpRequest(req: RequestHelp, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.sendHelpRequestAsync(req)
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

    override fun getNotificationData(apiCallback: ApiCallback<Response<NotificationDataResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.getNotificationDataAsync()
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

    override fun setSwitch(req: NotificationDataRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.setNotificationAsync(req)
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

    override fun changeSecurity(req: SecurityRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.changeSecurityRequestAsync(req)
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

    override fun changeSecuritySwitch(req: SecurityRequestSwitch, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.changeSecuritySwitchRequestAsync(req)
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

    override fun userProfileListAsync(apiCallback: ApiCallback<Response<UserProfileListResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.userProfileListAsync()
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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


    override fun createNewProfile(req: Map<String, RequestBody>, profile_picture: MultipartBody.Part, apiCallback: ApiCallback<Response<UserProfileListResponseWithoutList>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.createNewProfileAsync(req, profile_picture)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun createPaymentAsync(req: Map<String, RequestBody>, apiCallback: ApiCallback<Response<PaymentResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.createPaymentAsync(req)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun generateKeyToken(data: Map<String, String>, apiCallback: ApiCallback<Response<KeyResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.generateKeyTokenAsync(data)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun updateToken(data: Map<String, String>, apiCallback: ApiCallback<Response<KeyResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.updateTokenAsync(data)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun associateAppList(apiCallback: ApiCallback<Response<AssociateAppListResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.associateAppListAsync()
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun verifyEmail(data: Map<String, String>, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.verifyEmailAsync(data)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun sendOtp(data: Map<String, String>, apiCallback: ApiCallback<Response<OtpResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.sendOtpAsync(data)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun verifyPhone(data: Map<String, String>, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.verifyPhoneAsync(data)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun deleteuserPayment(id: String, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.deleteuserPaymentAsync(id)
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

    override fun setDefaultPayment(req: Map<String, RequestBody>, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.setDefaultPaymentMethodAsync(req)
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

    override fun updateProfileAsync(req: Map<String, RequestBody>, profile_picture: MultipartBody.Part?, apiCallback: ApiCallback<Response<UserProfileListResponseWithoutList>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.updateProfileAsync(req, profile_picture)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun deleteUserProfile(id: String, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.deleteUserProfileAsync(id)
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

    override fun notificationList(apiCallback: ApiCallback<Response<NotificationListResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.getNotificationListAsync()
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

    override fun isReadNotification(data: Map<String, String>, apiCallback: ApiCallback<Response<NewCommanResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.isReadNotificationAsync(data)
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

    override fun approveRequest(data: Map<String, String>, apiCallback: ApiCallback<Response<ApproVeResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.approveRequestAsync(data)
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

    override fun registerPush(data: Map<String, String>, apiCallback: ApiCallback<Response<ApiResponse<Any>>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.registerPushAsync(data)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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

    override fun transactionHistory(apiCallback: ApiCallback<Response<TransactionHistoryResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.transactionHistoryAsync()
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


    override fun tokenList(apiCallback: ApiCallback<Response<TokenListResponse>>) {
        apiCallback.onLoading()
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.tokenListAsync()
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


    override fun appUpdaterAsync(data: Map<String, String>, apiCallback: ApiCallback<Response<AppData>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = dashApi.appUpdaterAsync(data)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        apiCallback.onSuccess(response)
                    } else {
                        val apiRes = SimpleApiResponse()
                        val apiResponse: SimpleApiResponse? = Gson().fromJson(response.errorBody()?.string(), apiRes::class.java)
                        apiResponse?.message?.let { apiCallback.onFailed(it) }
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