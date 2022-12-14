package com.smart.sample.app.data.repo.dash

import com.smart.sample.app.data.beans.*
import com.smart.sample.app.data.beans.base.ApiResponse
import com.smart.sample.app.data.remote.helper.ApiCallback
import com.smart.sample.app.ui.main.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface DashRepo {
    //Latest delete other when you are done
    fun doLogin(email: String, password: String, apiCallback: ApiCallback<Response<SmartSampleAppLoginResponse>>)
    fun getCategory1(company_id: String, apiCallback: ApiCallback<Response<CategoryResponse>>)
    fun addItemAsync(
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
        barcode: String, location: String, min_stock: String, isAddItem: Boolean, apiCallback: ApiCallback<Response<DataItem>>
    )

    fun addCategory1(
        company_id: Int,
        name: String, apiCallback: ApiCallback<Response<AddCaResponse>>
    )


    fun getItemList(company_id: String, page:Int,apiCallback: ApiCallback<Response<ItemsListResponse>>)

    fun deleteItemAsync( itemId: String, apiCallback: ApiCallback<Response<CategoryResponse>>)


    //setting screen
    fun getUserInfo(apiCallback: ApiCallback<Response<LoginResponse>>)
    fun changeUserName(req: RequestUsernameChange, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun sendEmail(req: RequestEmailChange, apiCallback: ApiCallback<Response<SendOtpResponse>>)
    fun verifyOtp(req: VerifyEmailRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun changeEmail(req: ChangeEmailRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun sendOtpToPhone(req: SendOtpToPhoneRequest, apiCallback: ApiCallback<Response<SendPhoneOtpResponse>>)
    fun verifyPhoneOtp(req: VerifyEmailRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun getUserProfiles(apiCallback: ApiCallback<Response<UserProfileList>>)
    fun setDefaultProfile(req: SetDefaultProfile, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun getHelpList(apiCallback: ApiCallback<Response<HelpListResponse>>)
    fun sendHelpRequest(req: RequestHelp, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun getNotificationData(apiCallback: ApiCallback<Response<NotificationDataResponse>>)
    fun setSwitch(req: NotificationDataRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun changeSecurity(req: SecurityRequest, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun changeSecuritySwitch(req: SecurityRequestSwitch, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun userProfileListAsync(apiCallback: ApiCallback<Response<UserProfileListResponse>>)
    fun createNewProfile(req: Map<String, RequestBody>, profile_picture: MultipartBody.Part, apiCallback: ApiCallback<Response<UserProfileListResponseWithoutList>>)
    fun createPaymentAsync(req: Map<String, RequestBody>, apiCallback: ApiCallback<Response<PaymentResponse>>)
    fun generateKeyToken(data: Map<String, String>, apiCallback: ApiCallback<Response<KeyResponse>>)
    fun updateToken(data: Map<String, String>, apiCallback: ApiCallback<Response<KeyResponse>>)
    fun associateAppList(apiCallback: ApiCallback<Response<AssociateAppListResponse>>)
    fun verifyEmail(data: Map<String, String>, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun sendOtp(data: Map<String, String>, apiCallback: ApiCallback<Response<OtpResponse>>)
    fun verifyPhone(data: Map<String, String>, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun deleteuserPayment(id: String, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun setDefaultPayment(req: Map<String, RequestBody>, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun tokenList(apiCallback: ApiCallback<Response<TokenListResponse>>)

    fun updateProfileAsync(req: Map<String, RequestBody>, profile_picture: MultipartBody.Part?, apiCallback: ApiCallback<Response<UserProfileListResponseWithoutList>>)
    fun deleteUserProfile(id: String, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun notificationList(apiCallback: ApiCallback<Response<NotificationListResponse>>)
    fun isReadNotification(data: Map<String, String>, apiCallback: ApiCallback<Response<NewCommanResponse>>)
    fun approveRequest(data: Map<String, String>, apiCallback: ApiCallback<Response<ApproVeResponse>>)

    fun registerPush(data: Map<String, String>, apiCallback: ApiCallback<Response<ApiResponse<Any>>>)
    fun transactionHistory(apiCallback: ApiCallback<Response<TransactionHistoryResponse>>)
    fun appUpdaterAsync(data: Map<String, String>, apiCallback: ApiCallback<Response<AppData>>)


}