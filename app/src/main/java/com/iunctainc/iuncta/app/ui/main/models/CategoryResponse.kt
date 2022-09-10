package com.iunctainc.iuncta.app.ui.main.models

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,
    @field:SerializedName("error")
    val error: String? = null,
    @field:SerializedName("message")
    val message: String? = null
)

data class DataItem(

    @field:SerializedName("category1_id")
    val category1Id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("company")
    val company: Company? = null
)

data class Company(

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
