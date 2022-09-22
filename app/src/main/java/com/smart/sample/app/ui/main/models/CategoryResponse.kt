package com.smart.sample.app.ui.main.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryResponse(

    @field:SerializedName("data")
    val data: List<CategoryItem?>? = null,
    @field:SerializedName("error")
    val error: String? = null,
    @field:SerializedName("message")
    val message: String? = null
) : Serializable


data class AddCaResponse(
    @field:SerializedName("data")
    val data: CategoryItem? = null,
    @field:SerializedName("error")
    val error: String? = null,
    @field:SerializedName("message")
    val message: String? = null
) : Serializable



data class CategoryItem(

    @field:SerializedName("category1_id")
    val category1Id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("company")
    val company: Company? = null
) : Serializable

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
) : Serializable
