package com.iunctainc.iuncta.app.ui.main.home

import com.iunctainc.iuncta.app.data.beans.NewCommanResponse
import com.iunctainc.iuncta.app.data.beans.PaymentResponse
import com.iunctainc.iuncta.app.data.beans.UserProfileListResponseWithoutList
import com.iunctainc.iuncta.app.data.remote.helper.ApiCallback
import com.iunctainc.iuncta.app.data.remote.helper.NetworkErrorHandler
import com.iunctainc.iuncta.app.data.remote.helper.Resource
import com.iunctainc.iuncta.app.data.repo.dash.DashRepo
import com.iunctainc.iuncta.app.di.base.viewmodel.BaseViewModel
import com.iunctainc.iuncta.app.ui.main.models.ItemsListResponse
import com.iunctainc.iuncta.app.ui.main.models.SmartSaleLoginResponse
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class MainActivityVM @Inject constructor(private val dashRepo: DashRepo, private val networkErrorHandler: NetworkErrorHandler) : BaseViewModel() {
    val obrGetItemList = SingleRequestEvent<ItemsListResponse>()

    fun getItemList(companyId: String) {
        dashRepo.getItemList(companyId, object : ApiCallback<Response<ItemsListResponse>>() {
            override fun onSuccess(response: Response<ItemsListResponse>) {
                obrGetItemList.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrGetItemList.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrGetItemList.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrGetItemList.value = Resource.loading(null, "")
            }
        })
    }

}