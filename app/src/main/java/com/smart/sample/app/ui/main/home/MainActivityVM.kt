package com.smart.sample.app.ui.main.home

import com.smart.sample.app.data.remote.helper.ApiCallback
import com.smart.sample.app.data.remote.helper.NetworkErrorHandler
import com.smart.sample.app.data.remote.helper.Resource
import com.smart.sample.app.data.repo.dash.DashRepo
import com.smart.sample.app.di.base.viewmodel.BaseViewModel
import com.smart.sample.app.ui.main.models.CategoryResponse
import com.smart.sample.app.ui.main.models.ItemsListResponse
import com.smart.sample.app.util.event.SingleRequestEvent
import retrofit2.Response
import javax.inject.Inject

class MainActivityVM @Inject constructor(private val dashRepo: DashRepo, private val networkErrorHandler: NetworkErrorHandler) : BaseViewModel() {
    val obrGetItemList = SingleRequestEvent<ItemsListResponse>()
    val obrDelete = SingleRequestEvent<CategoryResponse>()

    fun getItemList(companyId: String, page: Int) {
        dashRepo.getItemList(companyId, page, object : ApiCallback<Response<ItemsListResponse>>() {
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

    fun deleteItemAsync(itemId: String) {
        dashRepo.deleteItemAsync(itemId, object : ApiCallback<Response<CategoryResponse>>() {
            override fun onSuccess(response: Response<CategoryResponse>) {
                obrDelete.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrDelete.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrDelete.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrDelete.value = Resource.loading(null, "")
            }
        })
    }
}