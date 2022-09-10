package com.iunctainc.iuncta.app.ui.main.additem

import com.iunctainc.iuncta.app.data.remote.helper.ApiCallback
import com.iunctainc.iuncta.app.data.remote.helper.NetworkErrorHandler
import com.iunctainc.iuncta.app.data.remote.helper.Resource
import com.iunctainc.iuncta.app.data.repo.dash.DashRepo
import com.iunctainc.iuncta.app.di.base.viewmodel.BaseViewModel
import com.iunctainc.iuncta.app.ui.main.models.CategoryResponse
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import retrofit2.Response
import javax.inject.Inject

class AddItemActivityVM @Inject constructor(private val dashRepo: DashRepo, private val networkErrorHandler: NetworkErrorHandler) : BaseViewModel() {
    val obrCategory1 = SingleRequestEvent<CategoryResponse>()

    fun getCategory1(companyId: String) {
        dashRepo.getCategory1(companyId, object : ApiCallback<Response<CategoryResponse>>() {
            override fun onSuccess(response: Response<CategoryResponse>) {
                obrCategory1.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrCategory1.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrCategory1.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrCategory1.value = Resource.loading(null, "")
            }
        })
    }

}