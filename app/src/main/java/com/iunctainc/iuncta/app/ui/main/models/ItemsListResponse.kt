package com.iunctainc.iuncta.app.ui.main.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ItemsListResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("meta")
    val meta: Meta? = null,

    @field:SerializedName("links")
    val links: Links? = null
) : Serializable

data class Category1(

    @field:SerializedName("category1_id")
    val category1Id: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
) : Serializable

data class Meta(

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("per_page")
    val perPage: Int? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("last_page")
    val lastPage: Int? = null,

    @field:SerializedName("from")
    val from: Int? = null,

    @field:SerializedName("links")
    val links: List<LinksItem?>? = null,

    @field:SerializedName("to")
    val to: Int? = null,

    @field:SerializedName("current_page")
    val currentPage: Int? = null
) : Serializable


data class Links(

    @field:SerializedName("next")
    val next: Any? = null,

    @field:SerializedName("last")
    val last: String? = null,

    @field:SerializedName("prev")
    val prev: Any? = null,

    @field:SerializedName("first")
    val first: String? = null
) : Serializable

data class LinksItem(

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("label")
    val label: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Serializable

data class DataItem(

    @field:SerializedName("category2")
    val category2: Category1? = null,

    @field:SerializedName("category3")
    val category3: Category1? = null,

    @field:SerializedName("item_id")
    val itemId: Int? = null,

    @field:SerializedName("opg_stock")
    val opgStock: Int? = null,

    @field:SerializedName("min_stock")
    val min_stock: Int? = null,

    @field:SerializedName("closing_stock")
    val closing_stock: Int? = null,

    @field:SerializedName("category1")
    val category1: Category1? = null,

    @field:SerializedName("vat")
    val vat: Int? = null,

    @field:SerializedName("discount")
    val discount: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("sales_price")
    val salesPrice: Double? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("company")
    val company: Company? = null,

    @field:SerializedName("barcode")
    val barcode: String? = null,

    @field:SerializedName("cost_price")
    val costPrice: Double? = null
) : Serializable
