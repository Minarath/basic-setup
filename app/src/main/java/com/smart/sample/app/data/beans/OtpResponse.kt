package com.smart.sample.app.data.beans

import com.google.gson.annotations.SerializedName

data class OtpResponse(

	@field:SerializedName("data")
	val data: OtpData = OtpData(),

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class OtpData(

	@field:SerializedName("otp")
	val otp: String = ""
)
