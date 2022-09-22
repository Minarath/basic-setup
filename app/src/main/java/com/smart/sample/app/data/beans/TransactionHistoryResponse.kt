package com.smart.sample.app.data.beans

import com.google.gson.annotations.SerializedName

data class TransactionHistoryResponse(

    @field:SerializedName("data")
    val data: ArrayList<TransactionHistoryData> = ArrayList(),

    @field:SerializedName("total_amount")
    val totalAmount: String = "",

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class TransactionHistoryData(

    @field:SerializedName("date")
    val date: String = "",

    @field:SerializedName("payment_type")
    val paymentType: Int = 0,

    @field:SerializedName("price")
    val price: String = "",

    @field:SerializedName("description")
    val description: String = "",

    @field:SerializedName("profile_picture")
    val profilePicture: String = "",

    @field:SerializedName("associate_name")
    val associateName: String = ""
)


data class TransactionDate(
    val date: String = "",
)

data class DateData(
    val date: String = "",
    val data: List<TransactionHistoryData>? = null
)