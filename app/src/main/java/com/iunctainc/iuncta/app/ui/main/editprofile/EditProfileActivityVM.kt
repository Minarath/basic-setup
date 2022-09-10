package com.iunctainc.iuncta.app.ui.main.editprofile

import com.iunctainc.iuncta.app.data.beans.NewCommanResponse
import com.iunctainc.iuncta.app.data.beans.PaymentResponse
import com.iunctainc.iuncta.app.data.beans.UserProfileListResponseWithoutList
import com.iunctainc.iuncta.app.data.remote.helper.ApiCallback
import com.iunctainc.iuncta.app.data.remote.helper.NetworkErrorHandler
import com.iunctainc.iuncta.app.data.remote.helper.Resource
import com.iunctainc.iuncta.app.data.repo.dash.DashRepo
import com.iunctainc.iuncta.app.di.base.viewmodel.BaseViewModel
import com.iunctainc.iuncta.app.util.event.SingleRequestEvent
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class EditProfileActivityVM @Inject constructor(private val dashRepo: DashRepo, private val networkErrorHandler: NetworkErrorHandler) : BaseViewModel() {
    val obrDeleteUserProfile = SingleRequestEvent<NewCommanResponse>()
    val obrDeletePaymentprofile = SingleRequestEvent<NewCommanResponse>()
    val obrDefaultpayment = SingleRequestEvent<NewCommanResponse>()
    val obrCreatePaymentProfileWise = SingleRequestEvent<PaymentResponse>()
    val obrEditProfile = SingleRequestEvent<UserProfileListResponseWithoutList>()

    fun editProfile(req: Map<String, RequestBody>, profile_picture: MultipartBody.Part?) {
        dashRepo.updateProfileAsync(req, profile_picture, object : ApiCallback<Response<UserProfileListResponseWithoutList>>() {
            override fun onSuccess(response: Response<UserProfileListResponseWithoutList>) {
                obrEditProfile.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrEditProfile.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrEditProfile.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrEditProfile.value = Resource.loading(null, "")
            }
        })
    }

    fun createPaymentProfileWise(req: Map<String, RequestBody>) {
        dashRepo.createPaymentAsync(req, object : ApiCallback<Response<PaymentResponse>>() {
            override fun onSuccess(response: Response<PaymentResponse>) {
                obrCreatePaymentProfileWise.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrCreatePaymentProfileWise.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrCreatePaymentProfileWise.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrCreatePaymentProfileWise.value = Resource.loading(null, "")
            }
        })
    }

    fun deletePaymentProfile(id: String) {
        dashRepo.deleteuserPayment(id, object : ApiCallback<Response<NewCommanResponse>>() {
            override fun onSuccess(response: Response<NewCommanResponse>) {
                obrDeletePaymentprofile.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrDeletePaymentprofile.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrDeletePaymentprofile.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrDeletePaymentprofile.value = Resource.loading(null, "")
            }
        })
    }

    fun setDefaultPayment(req: Map<String, RequestBody>) {
        dashRepo.setDefaultPayment(req, object : ApiCallback<Response<NewCommanResponse>>() {
            override fun onSuccess(response: Response<NewCommanResponse>) {
                obrDefaultpayment.value = Resource.success(response.body(), "OK")
            }

            override fun onFailed(message: String) {
                obrDefaultpayment.value = Resource.error(null, message)
            }

            override fun onErrorThrow(exception: Exception) {
                obrDefaultpayment.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }

            override fun onLoading() {
                obrDefaultpayment.value = Resource.loading(null, "")
            }
        })
    }

    fun deleteUserProfile(id: String) {
        dashRepo.deleteUserProfile(id, object : ApiCallback<Response<NewCommanResponse>>() {
            override fun onSuccess(response: Response<NewCommanResponse>) {
                obrDeleteUserProfile.value = Resource.success(response.body(), "OK")
            }
            override fun onFailed(message: String) {
                obrDeleteUserProfile.value = Resource.error(null, message)
            }
            override fun onErrorThrow(exception: Exception) {
                obrDeleteUserProfile.value = Resource.error(null, networkErrorHandler.getErrMsg(exception))
            }
            override fun onLoading() {
                obrDeleteUserProfile.value = Resource.loading(null, "")
            }
        })
    }
}