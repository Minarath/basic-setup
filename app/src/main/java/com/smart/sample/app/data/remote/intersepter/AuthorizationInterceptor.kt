package com.smart.sample.app.data.remote.intersepter

import com.smart.sample.app.di.base.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val mainResponse = chain.proceed(chain.request())
        if (mainResponse.code == 401) {
            MyApplication.instance?.restartApp()
        }
        return mainResponse
    }
}