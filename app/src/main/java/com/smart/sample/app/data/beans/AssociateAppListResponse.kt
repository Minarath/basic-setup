package com.smart.sample.app.data.beans

import com.google.gson.annotations.SerializedName

data class AssociateAppListResponse(

    @field:SerializedName("data")
    val data: ArrayList<AssociateAppList> = ArrayList(),

    @field:SerializedName("success")
    val success: Boolean = false,

    @field:SerializedName("message")
    val message: String? = null
)

data class AssociateAppList(

    @field:SerializedName("full_name")
    val fullName: String = "",

    @field:SerializedName("profile_picture")
    val profilePicture: String = "",

    @field:SerializedName("client_id")
    val clientId: Int = 0,

    @field:SerializedName("email")
    val email: String = "",

    @field:SerializedName("username")
    val username: String = ""
)
