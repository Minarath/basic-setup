package com.smart.sample.app.data.beans

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("firstname")
    var firstname: String = "",

    @SerializedName("lastname")
    var lastname: String = "",

    @SerializedName("gender")
    var gender: Int = 0,

    @SerializedName("birth_date")
    var birthDate: String = "",

    @SerializedName("email")
    var email: String = "",

    @SerializedName("password")
    var password: String = "",
)