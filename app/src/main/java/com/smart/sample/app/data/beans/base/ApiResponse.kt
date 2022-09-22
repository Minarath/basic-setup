package com.smart.sample.app.data.beans.base

import com.google.gson.annotations.SerializedName

class ApiResponse<Any> : SimpleApiResponse() {
    @SerializedName("data")
    var data: Any? = null
}