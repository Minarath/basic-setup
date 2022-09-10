package com.iunctainc.iuncta.app.data.beans

import com.google.gson.annotations.SerializedName

data class UserBean(
    @SerializedName("email")
    var email: String = "",

    @SerializedName("first_name")
    var firstName: String = "",

    @SerializedName("last_name")
    var lastName: String = "",

    @SerializedName("phone_number")
    var phoneNumber: String = "",

    @SerializedName("userId")
    val id: Int = 0,

    @SerializedName("username")
    var username: String = "",

    @SerializedName("gender")
    var gender: String = "",

    @SerializedName("latitude")
    var latitude: String = "",

    @SerializedName("longitude")
    var longitude: String = "",

    @SerializedName("profile_picture")
    val profilePhoto: String = "",

    @SerializedName("authentication")
    var authentication: Authentication = Authentication("", 0, "", "")
)

data class Authentication(
    @SerializedName("access_token")
    var accessToken: String = "",

    @SerializedName("expires_in")
    var expiresIn: Int = 0,

    @SerializedName("refresh_token")
    var refreshToken: String = "",

    @SerializedName("token_type")
    var tokenType: String = ""
)