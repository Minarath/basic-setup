package com.iunctainc.iuncta.app.data.remote.intersepter

import android.util.Log
import com.iunctainc.iuncta.app.di.base.MyApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.nio.charset.Charset

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Content-Type", "application/json")
        builder.addHeader("Accept", "application/json")
        try {
            builder.addHeader("Authorization", MyApplication.instance?.getToken().toString())
        } catch (e: Exception) {
            Log.e(">>>", "intercept: token exceptions" )
        }
        val response = chain.proceed(builder.build())

        val respString = getResponseString(response)
        //Log.i("respString", "intercept: $respString")

        return response
    }

    private fun getResponseString(response: Response): String? {
        val responseBody = response.body
        val source = responseBody?.source()
        source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source?.buffer
        var charset: Charset? = Charset.forName("UTF-8")
        val contentType = responseBody?.contentType()
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"))
        }
        if (charset == null) {
            return ""
        }
        return buffer?.clone()?.readString(charset)
    }
}