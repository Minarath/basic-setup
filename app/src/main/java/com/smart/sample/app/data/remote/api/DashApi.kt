package com.smart.sample.app.data.remote.api

import com.smart.sample.app.data.beans.*
import com.smart.sample.app.data.beans.base.ApiResponse
import com.smart.sample.app.ui.main.models.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface DashApi {

    @FormUrlEncoded
    @POST(EndPoints.Auth.login)
    fun doLoginAsync(@Field("email") email: String, @Field("password") password: String): Deferred<Response<SmartSampleAppLoginResponse>>

    @GET(EndPoints.Auth.getCategory1)
    fun getCategory1Async(@Query("company_id") companyId: String): Deferred<Response<CategoryResponse>>

    @GET(EndPoints.Auth.addItems)
    fun getItemListAsync(@Query("company_id") companyId: String,@Query("page") page:Int): Deferred<Response<ItemsListResponse>>

    @FormUrlEncoded
    @POST(EndPoints.Auth.getCategory1)
    fun addCategory1Async(
        @Field("company_id") company_id: Int,
        @Field("name") sales_price: String
    ): Deferred<Response<AddCaResponse>>

    @FormUrlEncoded
    @POST(EndPoints.Auth.addItems)
    fun addItemAsync(
        @Field("company_id") company_id: String,
        @Field("sales_price") sales_price: String,
        @Field("cost_price") cost_price: String,
        @Field("opg_stock") opg_stock: String,
        @Field("vat") vat: String,
        @Field("discount") discount: String,
        @Field("category1_id") category1_id: String,
        @Field("category2_id") category2_id: String?,
        @Field("category3_id") category3_id: String?,
        @Field("name") name: String,
        @Field("barcode") barcode: String,
        @Field("location") location: String,
        @Field("min_stock") min_stock: String
    ): Deferred<Response<DataItem>>

    @FormUrlEncoded
    @POST(EndPoints.Auth.updateItem)
    fun updateItemAsync(
        @Path("id") id: String,
        @Field("company_id") company_id: String,
        @Field("sales_price") sales_price: String,
        @Field("cost_price") cost_price: String,
        @Field("opg_stock") opg_stock: String,
        @Field("vat") vat: String,
        @Field("discount") discount: String,
        @Field("category1_id") category1_id: String,
        @Field("category2_id") category2_id: String?,
        @Field("category3_id") category3_id: String?,
        @Field("name") name: String,
        @Field("barcode") barcode: String,
        @Field("location") location: String,
        @Field("min_stock") min_stock: String
    ): Deferred<Response<DataItem>>

    @DELETE(EndPoints.Auth.updateItem)
    fun deleteItemAsync(@Path("id") id: String): Deferred<Response<CategoryResponse>>

    @GET(EndPoints.Auth.userInfor)
    fun getUserInfoAsync(): Deferred<Response<LoginResponse>>

    @POST(EndPoints.Auth.change_username)
    fun changeUserNameAsync(@Body user: RequestUsernameChange): Deferred<Response<NewCommanResponse>>

    @POST(EndPoints.Auth.sendEmail)
    fun sendOtpAsync(@Body user: RequestEmailChange): Deferred<Response<SendOtpResponse>>

    @POST(EndPoints.Auth.verifyOtpEmail)
    fun verifyOtpAsync(@Body user: VerifyEmailRequest): Deferred<Response<NewCommanResponse>>

    @POST(EndPoints.Auth.changeNewEmail)
    fun changeEmailAsync(@Body user: ChangeEmailRequest): Deferred<Response<NewCommanResponse>>


    @POST(EndPoints.Auth.sendOtpToPhone)
    fun sendOtpToPhoneAsync(@Body user: SendOtpToPhoneRequest): Deferred<Response<SendPhoneOtpResponse>>

    @POST(EndPoints.Auth.verifyChangePhoneOtp)
    fun verifyOtpPhoneAsync(@Body user: VerifyEmailRequest): Deferred<Response<NewCommanResponse>>

    @GET(EndPoints.Auth.getDefaultProfile)
    fun getUserProfileListAsync(): Deferred<Response<UserProfileList>>

    @POST(EndPoints.Auth.setDefaultProfile)
    fun setDefaultProfileAsync(@Body user: SetDefaultProfile): Deferred<Response<NewCommanResponse>>


    @GET(EndPoints.Auth.getHelplist)
    fun getHelpListAsync(): Deferred<Response<HelpListResponse>>


    @POST(EndPoints.Auth.sendUserHelp)
    fun sendHelpRequestAsync(@Body user: RequestHelp): Deferred<Response<NewCommanResponse>>

    @GET(EndPoints.Auth.getNotificationData)
    fun getNotificationDataAsync(): Deferred<Response<NotificationDataResponse>>


    @POST(EndPoints.Auth.setNotification)
    fun setNotificationAsync(@Body user: NotificationDataRequest): Deferred<Response<NewCommanResponse>>


    @POST(EndPoints.Auth.changeSecurity)
    fun changeSecurityRequestAsync(@Body user: SecurityRequest): Deferred<Response<NewCommanResponse>>

    @POST(EndPoints.Auth.changeSecurity)
    fun changeSecuritySwitchRequestAsync(@Body user: SecurityRequestSwitch): Deferred<Response<NewCommanResponse>>


    @GET(EndPoints.Auth.userProfilelist)
    fun userProfileListAsync(): Deferred<Response<UserProfileListResponse>>


    @Multipart
    @POST(EndPoints.Auth.createUserProfile)
    fun createNewProfileAsync(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>, @Part profilePhoto: MultipartBody.Part
    ): Deferred<Response<UserProfileListResponseWithoutList>>

    @Multipart
    @POST(EndPoints.Auth.userPaymentProfileWise)
    fun createPaymentAsync(@PartMap data: Map<String, @JvmSuppressWildcards RequestBody>): Deferred<Response<PaymentResponse>>

    @Multipart
    @POST(EndPoints.Auth.setDefaultProfile)
    fun setDefaultProfile(@PartMap data: Map<String, @JvmSuppressWildcards RequestBody>): Deferred<Response<PaymentResponse>>

    @POST(EndPoints.Auth.generate_key_token)
    fun generateKeyTokenAsync(@Body data: Map<String, String>): Deferred<Response<KeyResponse>>

    @POST(EndPoints.Auth.update_token)
    fun updateTokenAsync(@Body data: Map<String, String>): Deferred<Response<KeyResponse>>

    @GET(EndPoints.Auth.associate_app_list)
    fun associateAppListAsync(): Deferred<Response<AssociateAppListResponse>>

    @POST(EndPoints.Auth.verify_email)
    fun verifyEmailAsync(@Body data: Map<String, String>): Deferred<Response<NewCommanResponse>>

    @POST(EndPoints.Auth.send_otp)
    fun sendOtpAsync(@Body data: Map<String, String>): Deferred<Response<OtpResponse>>

    @POST(EndPoints.Auth.verify_phn)
    fun verifyPhoneAsync(@Body data: Map<String, String>): Deferred<Response<NewCommanResponse>>

    @DELETE(EndPoints.Auth.deleteUserPaymentProfile)
    fun deleteuserPaymentAsync(@Path("id") id: String): Deferred<Response<NewCommanResponse>>

    @Multipart
    @POST(EndPoints.Auth.setDefaultPaymentMethod)
    fun setDefaultPaymentMethodAsync(@PartMap data: Map<String, @JvmSuppressWildcards RequestBody>): Deferred<Response<NewCommanResponse>>

    @GET(EndPoints.Auth.token_list)
    fun tokenListAsync(): Deferred<Response<TokenListResponse>>

    @Multipart
    @POST(EndPoints.Auth.updateProfile)
    fun updateProfileAsync(
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>, @Part profilePhoto: MultipartBody.Part?
    ): Deferred<Response<UserProfileListResponseWithoutList>>

    @DELETE(EndPoints.Auth.delete_user_profile)
    fun deleteUserProfileAsync(@Path("id") id: String): Deferred<Response<NewCommanResponse>>

    @GET(EndPoints.Auth.notification_list)
    fun getNotificationListAsync(): Deferred<Response<NotificationListResponse>>

    @POST(EndPoints.Auth.is_read_notification)
    fun isReadNotificationAsync(@Body data: Map<String, String>): Deferred<Response<NewCommanResponse>>

    @POST(EndPoints.Auth.approve_request)
    fun approveRequestAsync(@Body data: Map<String, String>): Deferred<Response<ApproVeResponse>>

    @POST(EndPoints.Auth.register_psh)
    fun registerPushAsync(@Body data: Map<String, String>): Deferred<Response<ApiResponse<Any>>>

    @GET(EndPoints.Auth.transaction_history)
    fun transactionHistoryAsync(): Deferred<Response<TransactionHistoryResponse>>

    @POST(EndPoints.Auth.appUpdater)
    fun appUpdaterAsync(@Body data: Map<String, String>): Deferred<Response<AppData>>

}

