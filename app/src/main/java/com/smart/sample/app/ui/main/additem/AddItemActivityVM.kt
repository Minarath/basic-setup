package com.smart.sample.app.ui.main.additem

import com.smart.sample.app.data.remote.helper.ApiCallback
import com.smart.sample.app.data.remote.helper.NetworkErrorHandler
import com.smart.sample.app.data.remote.helper.Resource
import com.smart.sample.app.data.repo.dash.DashRepo
import com.smart.sample.app.di.base.viewmodel.BaseViewModel
import com.smart.sample.app.ui.main.models.AddCaResponse
import com.smart.sample.app.ui.main.models.CategoryResponse
import com.smart.sample.app.ui.main.models.DataItem
import com.smart.sample.app.util.event.SingleRequestEvent
import retrofit2.Response
import javax.inject.Inject

class AddItemActivityVM @Inject constructor(private val dashRepo: DashRepo, private val networkErrorHandler: NetworkErrorHandler) : BaseViewModel() {
    val obrCategory1 = SingleRequestEvent<CategoryResponse>()
    val obrAddItem = SingleRequestEvent<DataItem>()
    val obrAddCategory = SingleRequestEvent<AddCaResponse>()

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
    fun addCat1(companyId: String,name:String) {
        dashRepo.addCategory1(companyId.toInt(),name, object : ApiCallback<Response<AddCaResponse>>() {
            override fun onSuccess(response: Response<AddCaResponse>) {
                obrAddCategory.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrAddCategory.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrAddCategory.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrAddCategory.value = Resource.loading(null, "")
            }
        })
    }

    fun obrAddItem(
        itemId:String?,
        company_id: String,
        sales_price: String,
        cost_price: String,
        opg_stock: String,
        vat: String,
        discount: String,
        category1_id: String,
        category2_id: String?,
        category3_id: String?,
        name: String,
        barcode: String,
        location: String,min_stock: String,isAddItem:Boolean
    ) {
        dashRepo.addItemAsync(itemId,company_id, sales_price, cost_price, opg_stock, vat, discount, category1_id, category2_id, category3_id, name, barcode, location,min_stock, isAddItem,object : ApiCallback<Response<DataItem>>() {
            override fun onSuccess(response: Response<DataItem>) {
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