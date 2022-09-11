package com.iunctainc.iuncta.app.ui.main.additem

import com.iunctainc.iuncta.app.data.remote.helper.ApiCallback
import com.iunctainc.iuncta.app.data.remote.helper.NetworkErrorHandler
import com.iunctainc.iuncta.app.data.remote.helper.Resource
import com.iunctainc.iuncta.app.data.repo.dash.DashRepo
import com.iunctainc.iuncta.app.di.base.viewmodel.BaseViewModel
import com.iunctainc.iuncta.app.ui.main.models.AddItemResponse
import com.iunctainc.iuncta.app.ui.main.models.CategoryResponse
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import retrofit2.Response
import javax.inject.Inject

class AddItemActivityVM @Inject constructor(private val dashRepo: DashRepo, private val networkErrorHandler: NetworkErrorHandler) : BaseViewModel() {
    val obrCategory1 = SingleRequestEvent<CategoryResponse>()
    val obrAddItem = SingleRequestEvent<AddItemResponse>()

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

    fun obrAddItem(
        company_id: Int,
        sales_price: Int,
        cost_price: Int,
        opg_stock: Int,
        vat: Int,
        discount: Int,
        category1_id: Int,
        category2_id: Int?,
        category3_id: Int?,
        name: String,
        barcode: String
    ) {
        dashRepo.addItemAsync(company_id, sales_price, cost_price, opg_stock, vat, discount, category1_id, category2_id, category3_id, name, barcode, object : ApiCallback<Response<AddItemResponse>>() {
            override fun onSuccess(response: Response<AddItemResponse>) {
                obrAddItem.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrAddItem.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrAddItem.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrAddItem.value = Resource.loading(null, "")
            }
        })
    }

}