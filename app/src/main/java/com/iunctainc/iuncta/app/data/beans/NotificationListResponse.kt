package com.iunctainc.iuncta.app.data.beans

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationListResponse(

    @field:SerializedName("data")
    val data: ArrayList<NotificationListData> = ArrayList(),

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Serializable

data class NotificationListData(

    @field:SerializedName("payment_details")
    val paymentDetails: String? = null,

    @field:SerializedName("login_type")
    val loginType: Int? = null,

    @field:SerializedName("icon")
    val icon: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("profile_info")
    val profileInfo: ProfileInfo = ProfileInfo(),

    @field:SerializedName("notification_id")
    val notificationId: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("type")
    val type: Int? = null,

    @field:SerializedName("send_by")
    val sendBy: Int? = null,

    @field:SerializedName("is_read")
    val isRead: Int? = null,

    @field:SerializedName("send_to")
    val sendTo: Int? = null,

    @field:SerializedName("verify")
    var verify: Int? = null,

    @field:SerializedName("request_param")
    val requestParam: String? = null,

    @field:SerializedName("client_name")
    val clientName: String? = null,

    @field:SerializedName("client_image")
    val clientImage: String? = null,

    @field:SerializedName("profile_full_name")
    val profileFullName: String? = null
) : Serializable


data class ProfileInfo(
    @SerializedName("Address")
    var address: String? = "",
    @SerializedName("Birthdate")
    var birthdate: String? = "",
    @SerializedName("City")
    var city: String? = "",
    @SerializedName("Company name")
    var companyName: String? = "",
    @SerializedName("Country")
    var country: String? = "",
    @SerializedName("Email")
    var email: String? = "",
    @SerializedName("Full name")
    var fullName: String? = "",
    @SerializedName("Gender")
    var gender: String? = "",
    @SerializedName("Phone number")
    var phoneNumber: String? = "",
    @SerializedName("Position")
    var position: String? = "",
    @SerializedName("Profile picture")
    var profilePicture: String? = "",
    @SerializedName("State")
    var state: String? = "",
    @SerializedName("Website")
    var website: String? = ""
) : Serializable
