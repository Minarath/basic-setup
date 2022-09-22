package com.smart.sample.app.ui.main.login

import com.smart.sample.app.data.remote.helper.ApiCallback
import com.smart.sample.app.data.remote.helper.NetworkErrorHandler
import com.smart.sample.app.data.remote.helper.Resource
import com.smart.sample.app.data.repo.dash.DashRepo
import com.smart.sample.app.di.base.viewmodel.BaseViewModel
import com.smart.sample.app.ui.main.models.SmartSampleAppLoginResponse
import com.smart.sample.app.util.event.SingleRequestEvent
import retrofit2.Response
import javax.inject.Inject

class LoginActivityVM @Inject constructor(private val dashRepo: DashRepo, private val networkErrorHandler: NetworkErrorHandler) : BaseViewModel() {
    val obrLogin = SingleRequestEvent<SmartSampleAppLoginResponse>()

    fun doLogin(email: String, password: String) {
        dashRepo.doLogin(email, password, object : ApiCallback<Response<SmartSampleAppLoginResponse>>() {
            override fun onSuccess(response: Response<SmartSampleAppLoginResponse>) {
                obrLogin.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrLogin.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrLogin.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrLogin.value = Resource.loading(null, "")
            }
        })
    }

}