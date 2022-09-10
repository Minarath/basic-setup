package com.iunctainc.iuncta.app.data.beans

import com.google.gson.annotations.SerializedName

data class KeyResponse(

    @field:SerializedName("data")
    val data: KeyData = KeyData(),

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class KeyData(

    @field:SerializedName("type_name")
    val typeName: String = "",

    @field:SerializedName("expiry_in")
    val expiryIn: String = "",

    @field:SerializedName("user_id")
    val userId: Int = 0,

    @field:SerializedName("verify")
    val verify: Int = 0,

    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("used")
    val used: String = "",

    @field:SerializedName("type")
    val type: String = "",

    @field:SerializedName("client_name")
    val clientName: String = "",

    @field:SerializedName("client_id")
    val clientId: String = "",

    @field:SerializedName("token")
    val token: String = "",

    @field:SerializedName("client_image")
    val clientImage: String = "",

    var isAdd: Boolean = false,
    var isSelected : Int = 0,
    @field:SerializedName("expiry_seconds")
    val expiry_seconds: String? = null
)

data class TokenListResponse(
    @field:SerializedName("data")
    val data: ArrayList<KeyData> = ArrayList(),

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
