package com.smart.sample.app.data.beans.base

import com.google.gson.annotations.SerializedName

open class SimpleApiResponse {
    @SerializedName("status")
    var status = false

    @SerializedName("message")
    var message: String = ""

    @SerializedName("method")
    var method: String = ""

    @SerializedName("success")
    var isSuccess = false
}