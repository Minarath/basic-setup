package com.iunctainc.iuncta.app.ui.main.models

import com.google.gson.annotations.SerializedName

data class SmartSaleLoginResponse(

    @field:SerializedName("data")
    val data: Data? = null,
    @field:SerializedName("error")
    val error: String? = null,
    @field:SerializedName("message")
    val message: String? = null

)

data class CompaniesItem(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("company_id")
    val companyId: Int? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("logo")
    val logo: String? = null
)

data class Data(

    @field:SerializedName("companies")
    val companies: List<CompaniesItem?>? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("auth")
    val auth: Auth? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)

data class Auth(

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("expires_in")
    val expiresIn: Int? = null
)
