package com.iunctainc.iuncta.app.data.beans

import com.google.gson.annotations.SerializedName

data class PaymentResponse(

	@field:SerializedName("data")
	val data: PaymentData = PaymentData(),

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class PaymentData(

	@field:SerializedName("account_number")
	val accountNumber: String = "",

	@field:SerializedName("country")
	val country: String = "",

	@field:SerializedName("cvv")
	val cvv: String = "",

	@field:SerializedName("card_number")
	val cardNumber: String = "",

	@field:SerializedName("expiry_date")
	val expiryDate: String = "",

	@field:SerializedName("routing_number")
	val routingNumber: String = "",

	@field:SerializedName("type")
	val type: String = "",

	@field:SerializedName("is_default")
	val isDefault: Any? = null,

	@field:SerializedName("ifsc_code")
	val ifscCode: String = "",

	@field:SerializedName("profile_payment_id")
	val profilePaymentId: Int = 0,

	@field:SerializedName("branch_name")
	val branchName: String = "",

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("bank_name")
	val bankName: String = ""
)
