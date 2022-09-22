package com.smart.sample.app.data.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**Created by Bhagirath Devmurari on 07,July,2021 **/
data class RegisterUserRequest(
    @SerializedName("email")
    var email: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("device_id")
    var device_id: String = "",
    @SerializedName("otp")
    var otp: String = "",
    @SerializedName("password_confirmation")
    var passwordConfirmation: String = "",
    @SerializedName("username")
    var username: String = ""
)

data class CommanResponse<T>(
    @SerializedName("data")
    var `data`: T? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("success")
    var success: Boolean = false
)

data class ApproVeResponse(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("success")
    var success: Boolean = false
)


data class LoginResponse(
    @SerializedName("data")
    var `data`: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)

data class Data(
    @SerializedName("auth")
    var auth: Auth = Auth(),
    @SerializedName("biometrics_enable")
    var biometricsEnable: Int? = null,
    @SerializedName("biometrics_text_message")
    var biometricsTextMessage: Int? = null,
    @SerializedName("device_id")
    var deviceId: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("is_first_time_login")
    var isFirstTimeLogin: Int? = null,
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("username")
    var username: String = "",
    @SerializedName("otp")
    var otp: String = "",
    @SerializedName("country_code")
    var countryCode: String = "",
    @SerializedName("default_profile")
    var defaultProfile: String = "",
    @SerializedName("phone_number")
    var phoneNumber: String = "",
    @SerializedName("order_id")
    var order_id: String = "",
    @SerializedName("amount")
    var amount: String = "",
    @SerializedName("transaction_id")
    var transaction_id: String = "",
    @SerializedName("payment_status")
    var payment_status: String = "",
    @SerializedName("payment_method")
    var payment_method: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("app_link")
    var app_link: String = ""
)


data class Auth(
    @SerializedName("accessToken")
    var accessToken: String = "",
    @SerializedName("expiresIn")
    var expiresIn: Int? = null,
    @SerializedName("refreshToken")
    var refreshToken: String = "",
    @SerializedName("tokenType")
    var tokenType: String = ""
)

data class NewCommanResponse(
    @SerializedName("data")
    var `data`: List<Any> = listOf(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)

data class RequestUsernameChange(
    @SerializedName("username")
    var username: String = ""
)

data class RequestHelp(
    @SerializedName("help_id")
    var help_id: String = "",
    @SerializedName("description")
    var description: String = ""
)


data class RequestEmailChange(
    @SerializedName("email")
    var email: String = ""
)

data class SendOtpResponse(
    @SerializedName("data")
    var datas: Datas = Datas(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)

data class VerifyEmailRequest(//also used in verify otp api
    @SerializedName("otp")
    var otp: String = ""
)

data class ChangeEmailRequest(
    @SerializedName("email")
    var email: String = ""
)

data class SetDefaultProfile(
    @SerializedName("user_profile_id")
    var user_profile_id: String = ""
)

data class SendOtpToPhoneRequest(
    @SerializedName("country_code")
    var country_code: String = "",
    @SerializedName("phone_number")
    var phone_number: String = ""
)


data class Datas(
    @SerializedName("email_otp")
    var emailOtp: String = ""
)

data class SendPhoneOtpResponse(
    @SerializedName("data")
    var data: Data = Data(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)

data class UserProfileList(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("data")
    var userProfileList: List<UserProfile> = listOf()
)

data class UserProfile(
    @SerializedName("address")
    var address: String = "",
    @SerializedName("associate_list")
    var associateList: ArrayList<AssociateListData> = ArrayList(),
    @SerializedName("birthdate")
    var birthdate: String = "",
    @SerializedName("city")
    var city: String = "",
    @SerializedName("company_name")
    var companyName: String = "",
    @SerializedName("country")
    var country: String = "",
    @SerializedName("country_code")
    var countryCode: String = "",
    @SerializedName("default_profile")
    var defaultProfile: Int? = null,
    @SerializedName("email")
    var email: String = "",
    @SerializedName("full_name")
    var fullName: String = "",
    @SerializedName("gender")
    var gender: Int? = null,
    @SerializedName("payment_method_list")
    var paymentMethodList: ArrayList<PaymentListItem> = ArrayList(),
    @SerializedName("phone_number")
    var phoneNumber: String = "",
    @SerializedName("position")
    var position: String = "",
    @SerializedName("profile_name")
    var profileName: String = "",
    @SerializedName("profile_picture")
    var profilePicture: String = "",
    @SerializedName("state")
    var state: String = "",
    @SerializedName("unit")
    var unit: String = "",
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("user_profile_id")
    var userProfileId: Int? = null,
    @SerializedName("verified_email")
    var verifiedEmail: Int? = null,
    @SerializedName("verified_phone")
    var verifiedPhone: Int? = null,
    @SerializedName("website")
    var website: String = ""
)

data class HelpListResponse(
    @SerializedName("data")
    var helpdata: Helpdata = Helpdata(),
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false
)

data class Helpdata(
    @SerializedName("help")
    var help: List<Help> = listOf()
)

data class Help(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("sub_help_topic")
    var subHelpTopic: List<SubHelpTopic> = listOf()
)

data class SubHelpTopic(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("parent_id")
    var parentId: Int = 0
)


data class NotificationDataResponse(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("data")
    var notificationData: NotificationData = NotificationData(),
    @SerializedName("success")
    var success: Boolean = false
)

data class NotificationData(
    @SerializedName("approve_all")
    var approveAll: Int? = null,
    @SerializedName("deny_all")
    var denyAll: Int? = null,
    @SerializedName("email")
    var email: Int? = null,
    @SerializedName("login")
    var login: Int? = null,
    @SerializedName("pause_all")
    var pauseAll: Int? = null,
    @SerializedName("payment")
    var payment: Int? = null,
    @SerializedName("profile_data")
    var profileData: Int? = null,
    @SerializedName("text")
    var text: Int? = null,
    @SerializedName("two_factor")
    var twoFactor: Int = 0
)


data class NotificationDataRequest(
    @SerializedName("approve_all")
    var approveAll: String = "0",
    @SerializedName("deny_all")
    var denyAll: String = "0",
    @SerializedName("email")
    var email: String = "0",
    @SerializedName("login")
    var login: String = "0",
    @SerializedName("pause_all")
    var pauseAll: String = "0",
    @SerializedName("payment")
    var payment: String = "0",
    @SerializedName("profile_data")
    var profileData: String = "0",
    @SerializedName("text")
    var text: String = "0",
    @SerializedName("two_factor")
    var twoFactor: String = "0"
)


data class SecurityRequest(
    @SerializedName("old_password")
    var oldPassword: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("password_confirmation")
    var passwordConfirmation: String = ""
)


data class addPaymentMethodProfileWise(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("card_number")
    var card_number: String = "",
    @SerializedName("expiry_date")
    var expiry_date: String = "",
    @SerializedName("cvv")
    var cvv: String = "",
    @SerializedName("user_profile_id")
    var user_profile_id: String = "",
    @SerializedName("type")
    var type: String = ""
)


data class SecurityRequestSwitch(
    @SerializedName("biometrics_enable")
    var biometricsEnable: String = "",
    @SerializedName("biometrics_text_message")
    var biometricsTextMessage: String = ""
)


data class UserProfileListResponse(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("data")
    var userProfileList: List<UserProfileListRes> = listOf()
)

data class UserProfileListRes(
    @SerializedName("address")
    var address: String? = null,
    @SerializedName("birthdate")
    var birthdate: String? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("company_name")
    var companyName: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("country_code")
    var countryCode: Integer? = null,
    @SerializedName("default_profile")
    var defaultProfile: Int? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("full_name")
    var fullName: String? = null,
    @SerializedName("gender")
    var gender: Int? = null,
    @SerializedName("payment_method_list")
    var paymentMethodList: ArrayList<PaymentListItem> = ArrayList(),
    @SerializedName("phone_number")
    var phoneNumber: String? = null,
    @SerializedName("position")
    var position: String? = null,
    @SerializedName("profile_name")
    var profileName: String? = null,
    @SerializedName("profile_picture")
    var profilePicture: String? = null,
    @SerializedName("state")
    var state: String? = null,
    @SerializedName("unit")
    var unit: String? = null,
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("user_profile_id")
    var userProfileId: Int? = null,
    @SerializedName("verified_email")
    var verifiedEmail: Int? = null,
    @SerializedName("verified_phone")
    var verifiedPhone: Int? = null,
    @SerializedName("website")
    var website: String = "",
    @SerializedName("associate_list")
    var associateList: ArrayList<AssociateList> = ArrayList(),
) : Serializable


data class UserProfileListResponseWithoutList(
    @SerializedName("message")
    var message: String = "",
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("data")
    var userProfileList: UserProfileListRes? = null
) : Serializable


data class PaymentListItem(
    @SerializedName("account_number")
    var accountNumber: String? = "",
    @SerializedName("bank_name")
    var bankName: String? = "",
    @SerializedName("branch_name")
    var branchName: String? = "",
    @SerializedName("card_number")
    var cardNumber: String? = "",
    @SerializedName("country")
    var country: String? = "",
    @SerializedName("cvv")
    var cvv: Int? = 0,
    @SerializedName("expiry_date")
    var expiryDate: String? = "",
    @SerializedName("ifsc_code")
    var ifscCode: String? = "",
    @SerializedName("is_default")
    var isDefault: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("profile_payment_id")
    var profilePaymentId: Int? = 0,
    @SerializedName("routing_number")
    var routingNumber: String? = "",
    @SerializedName("type")
    var type: Int? = 0
) : Serializable


data class PaymentDataForNotification(
    @SerializedName("order_id")
    var orderId: String? = "",
    @SerializedName("product_details")
    var productDetails: List<ProductDetail>? = listOf(),
    @SerializedName("sub_total")
    var subTotal: String? = "",
    @SerializedName("tax_amount")
    var taxAmount: String? = "",
    @SerializedName("total_amount")
    var totalAmount: String? = ""
) : Serializable

data class ProductDetail(
    @SerializedName("amount")
    var amount: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("discount")
    var discount: String? = "",
    @SerializedName("final_amount")
    var finalAmount: String? = "",
    @SerializedName("product_id")
    var productId: String? = "",
    @SerializedName("title")
    var title: String? = ""
) : Serializable


data class FieldData(
    var fieldName: String? = ""
) : Serializable

data class AssociateListData(
    @SerializedName("client_id")
    var client_id: Int = 0,
    @SerializedName("full_name")
    var full_name: String = "",
    @SerializedName("username")
    var username: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("profile_picture")
    var profile_picture: String = ""
) : Serializable

data class AssociateList(
    @SerializedName("client_id")
    var client_id: String = "",
    @SerializedName("full_name")
    var full_name: String = "",
    @SerializedName("username")
    var username: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("profile_picture")
    var profile_picture: String = ""

) : Serializable

data class AppData(
    @SerializedName("data")
    var data: Data = Data(),
    @SerializedName("message")
    var message: String = ""
) : Serializable
