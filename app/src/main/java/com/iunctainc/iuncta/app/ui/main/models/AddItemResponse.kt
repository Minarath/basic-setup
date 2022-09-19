package com.iunctainc.iuncta.app.ui.main.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddItemResponse(

	@field:SerializedName("data")
	val addItemResponse: AddItemResponse? = null,

	@field:SerializedName("category2")
	val category2: Any? = null,

	@field:SerializedName("category3")
	val category3: Any? = null,

	@field:SerializedName("item_id")
	val itemId: Int? = null,

	@field:SerializedName("opg_stock")
	val opgStock: Int? = null,

	@field:SerializedName("min_stock")
	val min_stock: Int? = null,

	@field:SerializedName("category1")
	val category1: Category1? = null,

	@field:SerializedName("vat")
	val vat: Int? = null,

	@field:SerializedName("closing_stock")
	val closing_stock: Int? = null,

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
):Serializable

